package org.mariuxdeangelo.masterthesis.generators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cyclonedx.BomParserFactory;
import org.cyclonedx.model.Bom;
import org.mariuxdeangelo.masterthesis.database.DatabaseTableDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spdx.cdx2spdx.CycloneConversionException;
import org.spdx.cdx2spdx.CycloneSpdxConverter;
import org.spdx.jacksonstore.MultiFormatStore;
import org.spdx.library.InvalidSPDXAnalysisException;
import org.spdx.library.ModelCopyManager;
import org.spdx.library.model.SpdxDocument;
import org.spdx.library.model.SpdxModelFactory;
import org.spdx.storage.IModelStore;
import org.spdx.storage.simple.InMemSpdxStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class StaticHelper {

    public static final Logger logger = LoggerFactory.getLogger(StaticHelper.class);

    private static final DatabaseTableDAO databaseTableDAO = new DatabaseTableDAO();

    public static DatabaseTableDAO getDatabaseTableDAO() {
        return databaseTableDAO;
    }

    public static File moveMicrosoftFilesAccordingly(Path outputDir, Path sbomName) {
        try {
            File resultFile = outputDir.resolve("_manifest")
                    .resolve("spdx_2.2")
                    .resolve("manifest.spdx.json").toFile();
            File destinationFile = sbomName.toFile();
            destinationFile.deleteOnExit();
            FileUtils.copyFile(resultFile, destinationFile);
            FileUtils.deleteDirectory(outputDir.resolve("_manifest").toFile());
            return destinationFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long convertSpdx2Cdx(File spdx, File cdx) {
        try {
            long time = convertSpdx2CdxWithCdx(spdx, cdx);
            if (cdx.exists() && (Files.size(cdx.toPath()) > 0)) return time;

            return convertSpdx2CdxWithSyft(spdx, cdx);
        } catch (IOException e) {}
        return -1;
    }

    public static long convertCdx2Spdx(File cdx, File spdx) {
        try {
            try {
                convertCdx2SpdxWithSpdx(cdx, spdx);
            } catch (Exception ignored) {}
            if (spdx.exists() && (Files.size(spdx.toPath()) > 0)) return 0;

            long time = convertSpdx2CdxWithCdx(cdx, spdx);
            if (spdx.exists() && (Files.size(spdx.toPath()) > 0)) return time;

            return convertCdx2SpdxWithSyft(cdx, spdx);
        } catch (IOException ignored) {}
        return -1;
    }

    public static long convertCdx2SpdxWithCdx(File cycloneDx, File spdx) {
        String command = String.format("cyclonedx-cli convert --input-file %s --output-file %s --input-format json --output-format spdxjson", cycloneDx, spdx);
        long executionTime = CommandExecuter.execute(command);
        logger.info("Convertion time: {}", executionTime);
        return executionTime;
    }

    public static long convertSpdx2CdxWithCdx(File spdx, File cycloneDx) {
        String command = String.format("cyclonedx-cli convert --input-file %s --output-file %s --input-format spdxjson --output-format json", spdx , cycloneDx);
        long executionTime = CommandExecuter.execute(command);
        logger.info("Convertion time: {}", executionTime);
        return executionTime;
    }

    public static long convertSpdx2CdxWithSyft(File spdx, File cycloneDx) {
        String command = String.format("syft convert %s -o cyclonedx-json=%s", spdx, cycloneDx);
        long executionTime = CommandExecuter.execute(command);
        logger.info("Convertion time: {}", executionTime);
        return executionTime;
    }

    public static long convertCdx2SpdxWithSyft(File cycloneDx, File spdx) {
        String command = String.format("syft convert %s -o spdx-json=%s", cycloneDx, spdx);
        long executionTime = CommandExecuter.execute(command);
        logger.info("Convertion time: {}", executionTime);
        return executionTime;
    }

    public static File convertCdx2SpdxWithSpdx(File cycloneDx, File spdx) {
        try (FileOutputStream output = new FileOutputStream(spdx)) {

            final Bom cdxBom = BomParserFactory.createParser(cycloneDx).parse(cycloneDx);
            final IModelStore store = new InMemSpdxStore();
            CycloneSpdxConverter converter = new CycloneSpdxConverter(cdxBom, store);
            converter.convert();
            SpdxDocument document = SpdxModelFactory.createSpdxDocument(store, converter.getDocumentUri(), new ModelCopyManager());

            MultiFormatStore exportStore = new MultiFormatStore(store, MultiFormatStore.Format.JSON_PRETTY);
            exportStore.serialize(converter.getDocumentUri(), output);
        } catch (IOException | CycloneConversionException | RuntimeException | InvalidSPDXAnalysisException e) {
            logger.error("An error occured while converting a cdx file to spdx {} {}", cycloneDx, e);
            e.printStackTrace();
        } finally {
            return spdx;
        }
    }

    public static JsonNode httpGetJsonRequest(String url) {
        return httpGetJsonRequest(url, null);
    }

    public static JsonNode httpGetJsonRequest(String url, Map<String, String> header) {
        try {
            CloseableHttpClient httpClients = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse response = httpClients.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null ) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(EntityUtils.toString(entity));
                return jsonNode;
            } else {
                return null;
            }

        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadFile(String fileURL, File output) throws IOException {
        output.toPath().getParent().toFile().mkdirs();
        URL url = new URL(fileURL);
        ReadableByteChannel channel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(output);
            fileOutputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            channel.close();
        }
    }

    public static Path createNewSpdxPath(String uuid) {
        return createNewOutputPath("spdx", uuid);
    }

    public static Path createNewCdxPath(String uuid) {
        return createNewOutputPath("cdx", uuid);
    }

    public static Path getOwnWorkDir(String uuid) {
        Path output = GeneratorAppConfig.getBaseDir().resolve("workDir").resolve(uuid);
        output.toFile().mkdirs();
        return output;
    }

    public static Path createNewOutputPath(String label, String uuid) {
        Path output = GeneratorAppConfig.getBaseDir().resolve("workDir").resolve(uuid + "." + label + ".json");
        output.getParent().toFile().mkdirs();
        return output;
    }

    public static Path createNewOutputPathCast(String label, String uuid) {
        Path output = GeneratorAppConfig.getBaseDir().resolve("workDir").resolve(uuid + "." + label + ".cast");
        output.getParent().toFile().mkdirs();
        return output;
    }

    public static String readJsonFileToString(File jsonFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonFile);
            return node.toPrettyString();
        } catch (IOException e) {
            logger.error("File could not be converted to a valid Json String {}", jsonFile);
        }
        return null;
    }

    public static byte[] readFileToByteArray(Path file) {
        try {
            if (file.toFile().exists()) {
                return Files.readAllBytes(file);
            }
        } catch(IOException e) {
            logger.error("File could not be read {} {}", file, e);
        }
        return null;
    }
}
