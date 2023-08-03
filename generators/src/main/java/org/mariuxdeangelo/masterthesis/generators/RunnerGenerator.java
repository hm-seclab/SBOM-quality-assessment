package org.mariuxdeangelo.masterthesis.generators;

import org.kohsuke.github.GHRelease;
import org.mariuxdeangelo.masterthesis.database.DatabaseTableDAO;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SubjectProjectModel;
import org.mariuxdeangelo.masterthesis.generators.generators.*;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.Generator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RunnerGenerator implements Runnable {

    private final SubjectProjectModel subjectProject;

    public static final List<SbomFilesModel> existingSbomList = new DatabaseTableDAO().retrieveListOfSbomFilesLite();

    public static final Logger logger = LoggerFactory.getLogger(RunnerGenerator.class);

    public RunnerGenerator(SubjectProjectModel subjectProject) {
        this.subjectProject = subjectProject;
    }

    @Override
    public void run() {
        List<Generator> generatorList = new ArrayList<>();

        String activeContainer = GeneratorAppConfig.getGeneratorListContainer();
        if (subjectProject.getContainer() != null && !activeContainer.isBlank()) {
            if (subjectProject.getContainer().contains(":"))
                subjectProject.setContainer(subjectProject.getContainer() + ":latest");
            logger.info("Project {}: Container Scanner werden hinzugefügt.", subjectProject.getName());
            RetrieveSubjects.retrieveContainer(subjectProject.getContainer());
            if (activeContainer.contains("syft")) generatorList.add(new SyftGeneratorContainer(subjectProject.getContainer(), subjectProject.getProjectId()));
            if (activeContainer.contains("microsoft")) generatorList.add(new MicrosoftGeneratorContainer(subjectProject.getContainer(), subjectProject.getProjectId()));
            if (activeContainer.contains("trivy")) generatorList.add(new TrivyGeneratorContainer(subjectProject.getContainer(), subjectProject.getProjectId()));
            if (activeContainer.contains("cdxgen")) generatorList.add(new CycloneDxGeneratorContainer(subjectProject.getContainer(), subjectProject.getProjectId()));
            if (activeContainer.contains("tern")) generatorList.add(new TernGeneratorContainer(subjectProject.getContainer(), subjectProject.getProjectId()));
        }

        String activeRelease = GeneratorAppConfig.getGeneratorListrelease();
        GHRelease release = RetrieveSubjects.isReleaseAvailible(subjectProject.getGit(), subjectProject.getName());
        if (release != null && activeRelease != null) {
            logger.info("Project {}: release Scanner werden hinzugefügt.", subjectProject.getName());
            Path releasePath = RetrieveSubjects.retrieveRelease(release, subjectProject.getName());
            if (activeRelease.contains("syft")) generatorList.add(new SyftGeneratorRelease(releasePath, subjectProject.getProjectId()));
            if (activeRelease.contains("microsoft")) generatorList.add(new MicrosoftGeneratorRelease(releasePath, subjectProject.getProjectId()));
            if (activeRelease.contains("trivy")) generatorList.add(new TrivyGeneratorRelease(releasePath, subjectProject.getProjectId()));
            if (activeRelease.contains("cdxgen")) generatorList.add(new CycloneDxGeneratorRelease(releasePath, subjectProject.getProjectId()));
        }

        String activeSource = GeneratorAppConfig.getGeneratorListSource();
        if (subjectProject.getGit() != null && !activeSource.isBlank()) {
            logger.info("Project {}: Sources Scanner werden hinzugefügt.", subjectProject.getName());
//            Path sourcesPath = RetrieveSubjects.retrieveSources(subjectProject.getGit(), subjectProject.getName());
//            if (activeSource.contains("syft")) generatorList.add(new SyftGeneratorSources(sourcesPath, subjectProject.getProjectId()));
//            if (activeSource.contains("trivy")) generatorList.add(new TrivyGeneratorSources(sourcesPath, subjectProject.getProjectId()));
//            if (activeSource.contains("cdxgen")) generatorList.add(new CycloneDxGeneratorSources(sourcesPath, subjectProject.getProjectId()));
            if (activeSource.contains("github")) generatorList.add(new GithubGeneratorSources(subjectProject.getGit(), subjectProject.getProjectId()));
//            if (activeSource.contains("microsoft")) generatorList.add(new MicrosoftGeneratorSources(sourcesPath, subjectProject.getProjectId()));
//            if (activeSource.contains("scancode")) generatorList.add(new ScanCodeGeneratorSources(sourcesPath, subjectProject.getProjectId()));
        }

        logger.info("Project {}: {} Generators added", subjectProject.getName(), generatorList.size());

        for (Generator generator : generatorList) {
            try {
                boolean exists = existingSbomList.stream().anyMatch(g ->
                        g.getGenerator().equals(generator.generatorName()) &&
                                g.getMode().equals(generator.generatorMode()) &&
                                g.getProjectId() == subjectProject.getProjectId());

                // Check if regenerationof existing SBOMs is enabled
                if (exists && !GeneratorAppConfig.isRunRegenerateExistingSbom()) {
                    logger.info("Project {}: SBOM for {} {} already exists and will not be generated again.",
                            subjectProject.getName(), generator.generatorName(), generator.generatorMode());
                    continue;
                }

                logger.info("Project {}: Start Generating for {} {}",
                        subjectProject.getName(), generator.generatorName(), generator.generatorMode());

                // Generate SBOM
                SbomFilesModel generateResult = generator.generate();
                if (generateResult == null) {
                    logger.info("Project {}: Generator {} is canceled.",
                            subjectProject.getName(), generator.getClass().getName());
                    continue;
                } else {
                    logger.info("Project {}: Generator: {} Mode: {}",
                            subjectProject.getName(), generateResult.getGenerator(), generateResult.getMode());
                }

                // Write SBOM results to Database
                generateResult.setSbomFileId(StaticHelper.getDatabaseTableDAO().insertSbomFile(generateResult));

                // Runs the availible consumers over the SBOM
                if (GeneratorAppConfig.isRunConsumerAfterGenerator()) {
                    RunnerConsumer consumer = new RunnerConsumer(generateResult);
                    consumer.run();
                }
            } catch (Exception e) {
                logger.error("Project {}: bei der ausführung  des Generators {} {} ist ein fehler aufgetreten", e.getMessage(), generator.generatorName(), generator.generatorMode());
            }
        }
        logger.info("Generator for {} {} finished", subjectProject.getProjectId(), subjectProject.getName());
    }
}