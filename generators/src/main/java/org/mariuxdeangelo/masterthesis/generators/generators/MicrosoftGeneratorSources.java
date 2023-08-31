package org.mariuxdeangelo.masterthesis.generators.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorSources;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class MicrosoftGeneratorSources extends GeneratorSources {

    public MicrosoftGeneratorSources(Path sources, long projectId) {
        super(sources, projectId);
    }

    @Override
    public String generatorName() {
        return "microsoft";
    }

    @Override
    public synchronized SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path outputDir = StaticHelper.getOwnWorkDir(uuid);
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        outputDir.toFile().mkdirs();
        String command = String.format("sbom-tool generate -b %s -bc %s -pn test -pv 0.0.1 -ps testinstitution -nsb https://demo.org/demo", outputDir, getSources());
        long executionTime = CommandExecuter.executeRecorded(command, castOutput);

        SbomFilesModel result = new SbomFilesModel();
        StaticHelper.moveMicrosoftFilesAccordingly(outputDir, spdxOutput);
        StaticHelper.convertSpdx2CdxWithSyft(spdxOutput.toFile(), cdxOutput.toFile());
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
