package org.mariuxdeangelo.masterthesis.generators.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorContainer;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class TernGeneratorContainer extends GeneratorContainer {

    public TernGeneratorContainer(String container, long projectId) {
        super(container, projectId);
    }

    @Override
    public String generatorName() {
        return "tern";
    }

    @Override
    public SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        String command = String.format("/root/.local/bin/tern report -f spdxjson -i %s -o %s && /root/.local/bin/tern report -f cyclonedxjson -i %s -o %s", getContainer(), spdxOutput, getContainer(), cdxOutput);
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
