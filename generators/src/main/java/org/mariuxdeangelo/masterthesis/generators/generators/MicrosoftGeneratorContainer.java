package org.mariuxdeangelo.masterthesis.generators.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorContainer;

import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class MicrosoftGeneratorContainer extends GeneratorContainer {

    public MicrosoftGeneratorContainer(String container, long projectId) {
        super(container, projectId);
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
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        String command = String.format("sbom-tool generate -di %s -m %s -pn test -pv 0.0.1 -ps testinstitution -nsb https://demo.org/demo", getContainer(), outputDir);
        long executionTime = CommandExecuter.executeRecorded(command, castOutput);

        StaticHelper.moveMicrosoftFilesAccordingly(outputDir, spdxOutput);
        SbomFilesModel result = new SbomFilesModel();
        result.setProjectId(getProjectId());
        result.setExecutionTime(executionTime);
        result.setGenerator(generatorName());
        result.setMode(generatorMode());
        result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        result.setCommand(command);
        result.setShellOutput(StaticHelper.readFileToByteArray(castOutput));
        result.setSpdx(StaticHelper.readJsonFileToString(spdxOutput.toFile()));
        result.setOrig_spdx(true);
        result.setOrig_cdx(false);

        return result;
    }
}
