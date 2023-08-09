package org.mariuxdeangelo.masterthesis.generators.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorContainer;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class CycloneDxGeneratorContainer extends GeneratorContainer {

    public CycloneDxGeneratorContainer(String container, long projectId) {
        super(container, projectId);
    }

    @Override
    public String generatorName() {
        return "cdxgen";
    }

    @Override
    public SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        String command = String.format("cdxgen %s --deep --spec-version 1.4 -o %s -t docker", getContainer(), cdxOutput);
        long executionTime = CommandExecuter.executeRecorded(command, castOutput);

        StaticHelper.convertCdx2Spdx(cdxOutput.toFile(), spdxOutput.toFile());
        SbomFilesModel result = new SbomFilesModel();
        result.setProjectId(getProjectId());
        result.setExecutionTime(executionTime);
        result.setGenerator(generatorName());
        result.setMode(generatorMode());
        result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        result.setCommand(command);
        result.setShellOutput(StaticHelper.readFileToByteArray(castOutput));
        String spdxString = StaticHelper.readJsonFileToString(spdxOutput.toFile());
        result.setSpdx(spdxString != null && spdxString.isEmpty() ? null: spdxString);
        String cdxString = StaticHelper.readJsonFileToString(cdxOutput.toFile());
        result.setCdx(cdxString != null && cdxString.isEmpty() ? null: cdxString);
        result.setOrig_spdx(false);
        result.setOrig_cdx(true);

        return result;
    }
}
