package org.mariuxdeangelo.masterthesis.generators;


import org.mariuxdeangelo.masterthesis.database.DatabaseTableDAO;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SubjectProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootApplication
public class GeneratorApplication {

    private static boolean isFirst = false;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(GeneratorAppConfig.getThreadpoolSize());
    private static final Logger logger = LoggerFactory.getLogger(GeneratorApplication.class);
    private static List<Future<?>> futures = new ArrayList<>();

    public static void main(String args[]) {
        SpringApplication application = new SpringApplication(GeneratorApplication.class);
        application.addListeners((ApplicationListener<ContextRefreshedEvent>) event -> {
            if (GeneratorAppConfig.isRunGenerator()) runGenerators();
            if (GeneratorAppConfig.isRunConsumer()) runConsumer();

            try {
                threadPool.awaitTermination(9999, TimeUnit.HOURS);
                logger.info("SBOM Generator Finished.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        application.run(args);
    }

    private static void runGenerators() {
        List<SubjectProjectModel> subjectProjectList = StaticHelper.getDatabaseTableDAO().retrieveListOfSubjectProjects();

        for (SubjectProjectModel subject : subjectProjectList) {
//            if (subject.getProjectId() != 148) continue;
            RunnerGenerator generatorRunner = new RunnerGenerator(subject);
            logger.info("Add new Project to ExecuterService: {} {}", subject.getName(), subject.getProjectId());

            // First run is for initialisation of Scanners
            if (isFirst) {
                futures.add(threadPool.submit(generatorRunner));
            } else {
                generatorRunner.run();
                isFirst = true;
            }
        }

    }

    private static void runConsumer() {
        List<SbomFilesModel> sbomFilesModelList = StaticHelper.getDatabaseTableDAO().retrieveListOfSbomFilesLite();

        for (SbomFilesModel sbom : sbomFilesModelList) {
            SbomFilesModel fullModel = StaticHelper.getDatabaseTableDAO().retrieveSbomFile(sbom.getSbomFileId());
            RunnerConsumer consumer = new RunnerConsumer(fullModel);
            logger.info("Added new Consumer for SBOM to ExecuterService: {}", sbom.getSbomFileId());
            futures.add(threadPool.submit(consumer));
        }
    }

    private static void killDeadTasks() {
        for (Future<?> future : futures) {
            try {
                future.get(1, TimeUnit.HOURS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
