package org.mariuxdeangelo.masterthesis.generators.generators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorSources;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class ScanCodeGeneratorSources extends GeneratorSources {

    public ScanCodeGeneratorSources(Path sources, long projectId) {
        super(sources, projectId);
    }

    @Override
    public String generatorName() {
        return "scancode";
    }

    @Override
    public SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        Path cdxOutputOrig = StaticHelper.createNewCdxPath(uuid + ".orig");
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(generatorName(), uuid);
        String command = String.format("/root/.local/bin/scancode -clpi -n 10 --cyclonedx %s %s", cdxOutputOrig, getSources());
        long executionTime = CommandExecuter.executeRecorded(command, castOutput);

        // Scancode creates a properties element in the Metadata tab that is not a valid element for CycloneDx
        // To be able to process the file further the element is removed in the following.
        // But for this reason the file is flagged as "Converted" so also the cdx file is not orig.
        String cdxString = null;
        String spdxString = null;
        if (cdxOutputOrig.toFile().exists()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(cdxOutputOrig.toFile());
                ObjectNode metadata = (ObjectNode) node.get("metadata");
                metadata.remove("properties");
                mapper.writeValue(cdxOutput.toFile(), node);
                StaticHelper.convertCdx2Spdx(cdxOutput.toFile(), spdxOutput.toFile());
                cdxString = node.toPrettyString();
                spdxString = StaticHelper.readJsonFileToString(spdxOutput.toFile());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        SbomFilesModel result = new SbomFilesModel();
        result.setProjectId(getProjectId());
        result.setExecutionTime(executionTime);
        result.setGenerator(generatorName());
        result.setMode(generatorMode());
        result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        result.setCommand(command);
        result.setShellOutput(StaticHelper.readFileToByteArray(castOutput));
        result.setSpdx(spdxString != null && spdxString.isEmpty() ? null: spdxString);
        result.setCdx(cdxString != null && cdxString.isEmpty() ? null: cdxString);
        result.setOrig_spdx(false);
        result.setOrig_cdx(false);

        return result;
    }
}