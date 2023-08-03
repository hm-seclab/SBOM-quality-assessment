package org.mariuxdeangelo.masterthesis.generators.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorContainer;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class SyftGeneratorContainer extends GeneratorContainer {

    public SyftGeneratorContainer(String container, long projectId) {
        super(container, projectId);
    }

    @Override
    public String generatorName() {
        return "syft";
    }

    @Override
    public SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        String command = String.format("syft %s -o spdx-json=%s -o cyclonedx-json=%s", getContainer(), spdxOutput, cdxOutput);
        long executionTime = CommandExecuter.executeRecorded(command, castOutput);

        SbomFilesModel result = new SbomFilesModel();
        result.setProjectId(getProjectId());
        result.setExecutionTime(executionTime);
        result.setGenerator(generatorName());
        result.setMode(generatorMode());
        result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        result.setCommand(command);
        result.setShellOutput(StaticHelper.readFileToByteArray(castOutput));
        result.setSpdx(StaticHelper.readJsonFileToString(spdxOutput.toFile()));
        result.setCdx(StaticHelper.readJsonFileToString(cdxOutput.toFile()));
        result.setOrig_spdx(true);
        result.setOrig_cdx(true);

        return result;
    }
}




