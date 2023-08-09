package de.mariuxdeangelo.sbom.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotAllowedException;
import org.mariuxdeangelo.masterthesis.database.DatabaseViewDAO;
import org.mariuxdeangelo.masterthesis.database.modelsView.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SbomRestController {

    public static final Logger logger = LoggerFactory.getLogger(SbomRestController.class);

    private DatabaseViewDAO viewDao = new DatabaseViewDAO();

    private void rateDoc(EndpointType endpooint, Object ...params) {
        logger.info("Following endpoint was called: {} {}", endpooint, params);
        viewDao.insertApiUsage(endpooint);
    }

    @GetMapping("getSbomMetadata")
    public List<SbomMetadataViewModel> retrieveSbomMetadata() {
        rateDoc(EndpointType.RETRIEVE_SBOM_DATA);
        return viewDao.retrieveSbomMetadata();
    }

    @GetMapping("getSbomMetadataByProject")
    public List<SbomMetadataViewModel> retrieveSbomMetadataByProject(@RequestParam Long project_id) {
        rateDoc(EndpointType.RETRIEVE_SBOM_METADATA_BY_PROJECT, project_id);
        return viewDao.retrieveSbomMetadataByProject(project_id);
    }

    @GetMapping("getSbomMetadataByFile")
    public SbomMetadataViewModel retrieveSbomMetadataByFile(@RequestParam Long sbom_file_id) {
        rateDoc(EndpointType.RETRIEVE_SBOM_METADATA_BY_FILE, sbom_file_id);
        return viewDao.retrieveSbomMetadataByFile(sbom_file_id);
    }

    @GetMapping("getDependencyList")
    public List<DependencyListViewModel> retrieveDependencyList(@RequestParam Long project_id, @RequestParam EndpointType type) {
        rateDoc(type, project_id);

        switch (type) {
            case RETRIEVE_DEPENDENCY_LIST_BY_NAME:
                return viewDao.retrieveDependencyListByName(project_id);
            case RETRIEVE_DEPENDENCY_LIST_BY_NAME_VERSION:
                return viewDao.retrieveDependencyListByNameVersion(project_id);
            case RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC:
                return viewDao.retrieveDependencyListByRefLoc(project_id);
            case RETRIEVE_DEPENDENCY_LIST_BY_REF_LOC_VERSION:
                return viewDao.retrieveDependencyListByRefLocVersion(project_id);
            case RETRIEVE_CDX_DEPENDENCY_LIST_BY_NAME:
                return viewDao.retrieveCdxDependencyListByName(project_id);
            case RETRIEVE_CDX_DEPENDENCY_LIST_BY_NAME_VERSION:
                return viewDao.retrieveCdxDependencyListByNameWithVersion(project_id);
            case RETRIEVE_CDX_DEPENDENCY_LIST_BY_PURL:
                return viewDao.retrieveCdxDependencyListByPurl(project_id);
            case RETRIEVE_CDX_DEPENDENCY_LIST_BY_PURL_VERSION:
                return viewDao.retrieveCdxDependencyListByPurlWithVersion(project_id);
            default:
                throw new NotAllowedException("The attribute " + type + " is not allowed.");
        }
    }

    @GetMapping("getSpdxInsightsForFile")
    public SpdxInsightsModel retrieveSpdxInsightsForFile(@RequestParam Long sbom_file_id) {
        rateDoc(EndpointType.RETRIEVE_SPDX_INSIGHTS_FOR_FILE, sbom_file_id);
        return viewDao.retrieveSpdxInsightsByFile(sbom_file_id);
    }

    @GetMapping("getSpdxInsightsForProject")
    public List<SpdxInsightsModel> retrieveSpdxInsightsForProject(@RequestParam Long project_id) {
        rateDoc(EndpointType.RETRIEVE_SPDX_INSIGHTS_FOR_PROJECT, project_id);
        return viewDao.retrieveSpdxInsightsByProject(project_id);
    }

    @GetMapping("getLicenseList")
    public List<LicenseListViewModel> retrieveLicenseList(@RequestParam Long project_id, String attribute) {
        rateDoc(EndpointType.RETRIEVE_LICENSE_LIST, project_id, attribute);
        if (attribute.equals("copyrightText")
                || attribute.equals("filesAnalyzed")
                || attribute.equals("licenseDeclared")
                || attribute.equals("downloadLocation")
                || attribute.equals("licenseConcluded")) {
            return viewDao.retrieveLicenseList(project_id, attribute);
        } else {
            throw new NotAllowedException("The attribute " + attribute + " is not allowed.");
        }
    }

    @GetMapping
    @RequestMapping("downloadSbomFile")
    public ResponseEntity<InputStreamResource> downloadSbomFile(@RequestParam Long sbom_file_id, @RequestParam String type) throws IOException {
        rateDoc(EndpointType.DOWNLOAD_SBOM_FILE, sbom_file_id, type);
        SbomDownloadViewModel downloadViewModel = viewDao.retrieveSbomDownload(sbom_file_id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = downloadViewModel.getName() + "-" +
                          downloadViewModel.getGenerator() + "-" +
                          downloadViewModel.getMode() + "-" +
                          dateFormat.format(downloadViewModel.getTimestamp()) + "." +
                          type + ".json";

        InputStream inputStream;
        long length = 0;

        ObjectMapper mapper = new ObjectMapper();
        if (type.equals("spdx")) {
            JsonNode node = mapper.readTree(downloadViewModel.getSpdx());
            String spdxString = node.toPrettyString();
            inputStream = new ByteArrayInputStream(spdxString.getBytes(StandardCharsets.UTF_8));
            length = spdxString.length();
        } else if (type.equals("cdx")) {
            JsonNode node = mapper.readTree(downloadViewModel.getCdx());
            String cdxString = node.toPrettyString();
            inputStream = new ByteArrayInputStream(cdxString.getBytes(StandardCharsets.UTF_8));
            length = cdxString.length();
        } else {
            return null;
        }

        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(length)
                .body(resource);
    }

    @GetMapping
    @RequestMapping("downloadShellLogFile")
    public ResponseEntity<InputStreamResource> downloadShellLogFile(@RequestParam Long sbom_file_id) {
        rateDoc(EndpointType.DOWNLOAD_SHELL_LOG_FILE, sbom_file_id);
        SbomDownloadViewModel downloadViewModel = viewDao.retrieveSbomDownload(sbom_file_id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = downloadViewModel.getName() + "-" +
                downloadViewModel.getGenerator() + "-" +
                downloadViewModel.getMode() + "-" +
                dateFormat.format(downloadViewModel.getTimestamp()) + ".cast";

        InputStream inputStream = new ByteArrayInputStream(downloadViewModel.getShellLog());
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(downloadViewModel.getShellLog().length)
                .body(resource);
    }
}
