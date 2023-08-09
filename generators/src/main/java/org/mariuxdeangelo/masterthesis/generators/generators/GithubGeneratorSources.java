package org.mariuxdeangelo.masterthesis.generators.generators;

import com.fasterxml.jackson.databind.JsonNode;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.GeneratorAppConfig;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.GeneratorSources;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Github API generates a SPDX SBOM, but strictly speaking it's not a
 * SPDX file. They Wrap the file inside a sbom tag in json.
 */
public class GithubGeneratorSources extends GeneratorSources {

    private String url;

    public GithubGeneratorSources(String url, long projectId) {
        super(null, projectId);
        this.url = url;
    }

    @Override
    public String generatorName() {
        return "github";
    }

    @Override
    public SbomFilesModel generate() {
        String uuid = UUID.randomUUID().toString();
        Path spdxOutput = StaticHelper.createNewSpdxPath(uuid);
        Path cdxOutput = StaticHelper.createNewCdxPath(uuid);
        String apiUrl = url.replace("https://github.com/", "https://api.github.com/repos/") +
                "/dependency-graph/sbom";

        try {
            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Bearer " + GeneratorAppConfig.getGitToken());
            JsonNode jsonNode = StaticHelper.httpGetJsonRequest(apiUrl);

            if (jsonNode != null) {
                FileWriter fileWriter = new FileWriter(spdxOutput.toFile());
                fileWriter.write(jsonNode.get("sbom").toPrettyString());
                fileWriter.close();
                StaticHelper.convertSpdx2Cdx(spdxOutput.toFile(), cdxOutput.toFile());

                SbomFilesModel result = new SbomFilesModel();
                result.setProjectId(getProjectId());
                result.setGenerator(generatorName());
                result.setMode(generatorMode());
                result.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
                result.setSpdx(StaticHelper.readJsonFileToString(spdxOutput.toFile()));
                result.setCdx(StaticHelper.readJsonFileToString(cdxOutput.toFile()));
                result.setOrig_spdx(true);
                result.setOrig_cdx(false);

                return result;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
        return null;
    }
}
