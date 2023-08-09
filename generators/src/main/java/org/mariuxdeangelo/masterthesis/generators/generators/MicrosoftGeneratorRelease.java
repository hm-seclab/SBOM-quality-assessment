package org.mariuxdeangelo.masterthesis.generators.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorRelease;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class MicrosoftGeneratorRelease extends GeneratorRelease {

    public MicrosoftGeneratorRelease(Path repository, long projectId) {
        super(repository, projectId);
    }

    @Override
    public String generatorName() {
        return "microsoft";
    }

    @Override
    public SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path outputDir = StaticHelper.getOwnWorkDir(uuid);
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        outputDir.toFile().mkdirs();
        String command = String.format("sbom-tool generate -b %s -bc %s -pn test -ps testinstitution -nsb https://demo.org/demo -pv 0.0.1", outputDir, getRepository());
        long executionTime = CommandExecuter.executeRecorded(command, castOutput);

        StaticHelper.moveMicrosoftFilesAccordingly(outputDir, spdxOutput);
        StaticHelper.convertSpdx2Cdx(spdxOutput.toFile(), cdxOutput.toFile());
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
        result.setOrig_cdx(false);

        return result;
    }
}
