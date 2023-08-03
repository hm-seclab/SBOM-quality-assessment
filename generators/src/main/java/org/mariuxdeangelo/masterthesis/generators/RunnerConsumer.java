package org.mariuxdeangelo.masterthesis.generators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Multiset;
import org.mariuxdeangelo.masterthesis.database.DatabaseTableDAO;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.ConsumerModel;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.consumers.AbstractConsumer;
import org.mariuxdeangelo.masterthesis.generators.consumers.ConsumerGrype;
import org.mariuxdeangelo.masterthesis.generators.consumers.ConsumerOsvDev;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class RunnerConsumer implements Runnable{

    private final SbomFilesModel sbom;

    public static final List<ConsumerModel> existingConsumerList = new DatabaseTableDAO().retrieveConsumerListLite();

    public static final Logger logger = LoggerFactory.getLogger(RunnerConsumer.class);

    public RunnerConsumer(SbomFilesModel sbom) {
        this.sbom = sbom;
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();


        List<AbstractConsumer> consumerList = new ArrayList<>();
        consumerList.add(new ConsumerGrype());
        consumerList.add(new ConsumerOsvDev());

        try {
            for (AbstractConsumer consumer : consumerList) {
                Map<String, String> sbomMap = new HashMap<>();
                if (sbom.getSpdx() != null) sbomMap.put("spdx", sbom.getSpdx());
                if (sbom.getCdx() != null) sbomMap.put("cdx", sbom.getCdx());

                // If Regeneration of Consumer results is not wanted, here are the filters.
                if (!GeneratorAppConfig.isRegenerateExistingConsumer()) {
                    boolean spdxExists = existingConsumerList.stream().anyMatch(e ->
                            (sbom.getSbomFileId() == e.getSbomFileId()) &&
                                  e.getConsumer().equals(consumer.getConsumerName()) &&
                                  e.getType().equals("spdx"));
                    boolean cdxExists = existingConsumerList.stream().anyMatch(e ->
                            (sbom.getSbomFileId() == e.getSbomFileId()) &&
                                  e.getConsumer().equals(consumer.getConsumerName()) &&
                                  e.getType().equals("cdx"));

                    if (spdxExists) sbomMap.remove("spdx");
                    if (cdxExists) sbomMap.remove("cdx");
                }

                for (Map.Entry<String, String> sbomEntry : sbomMap.entrySet()) {
                    String type = sbomEntry.getKey();
                    String sbomString = sbomEntry.getValue();
                    JsonNode sbomNode = mapper.readTree(sbomString);
                    File consumerTempFile = StaticHelper.createNewOutputPath(type, UUID.randomUUID().toString()).toFile();
                    mapper.writeValue(consumerTempFile, sbomNode);

                    // Here the actual SBOM is consumed
                    File output = consumer.consumeSbom(consumerTempFile.toPath());
                    if (!output.exists() || !output.canRead()) {
                        logger.info("Consumer output from {} for {} {} sbomFileId is not availible",
                                consumer.getConsumerName(), type, sbom.getSbomFileId());
                        continue;
                    }

                    JsonNode consumerNode = mapper.readTree(output);
                    String report = consumerNode.toString();
                    if (report.isEmpty() || report.isBlank()) {
                        logger.info("Consumer output from {} for {} {} sbomFileId is empty or blank. {}",
                                consumer.getConsumerName(), type, sbom.getSbomFileId(), output);
                        continue;
                    }

                    ConsumerModel result = new ConsumerModel();
                    result.setConsumer(consumer.getConsumerName());
                    result.setType(type);
                    result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
                    result.setSbomFileId(sbom.getSbomFileId());
                    result.setReport(report);

                    StaticHelper.getDatabaseTableDAO().insertCunsumer(result);
                    logger.info("Consumer {} for SBOM {} type {} done",
                            consumer.getConsumerName(), sbom.getSbomFileId(), type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
