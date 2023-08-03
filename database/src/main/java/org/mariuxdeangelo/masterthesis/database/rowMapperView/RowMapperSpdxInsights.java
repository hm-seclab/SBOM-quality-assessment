package org.mariuxdeangelo.masterthesis.database.rowMapperView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mariuxdeangelo.masterthesis.database.modelsView.SpdxInsightsModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class RowMapperSpdxInsights implements RowMapper<SpdxInsightsModel> {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


    @Override
    public SpdxInsightsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SpdxInsightsModel result = new SpdxInsightsModel();

        try {
            result.setSbomFileId(rs.getLong("sbom_file_id"));
            result.setGenerator(rs.getString("generator"));
            result.setMode(rs.getString("mode"));
            result.setName(rs.getString("name"));
            result.setSpdxId(rs.getString("spdxid"));
            result.setComment(rs.getString("comment"));
            result.setDataLicense(rs.getString("datalicense"));
            result.setSpdxVersion(rs.getString("spdxversion"));
            result.setCreationDate(rs.getString("creationdate"));
            result.setLicenseListVersion(rs.getString("licenselistversion"));
            result.setCreators(convertArrayToMap(objectMapper.readValue(rs.getString("creators"), String[].class)));
            result.setDocumentNamespace(rs.getString("documentnamespace"));
            result.setTotal_packages(rs.getLong("total_packages"));
            result.setTotal_relationships(rs.getLong("total_relationships"));
            result.setTotal_hasExternalLicensingInfos(rs.getLong("total_hasexternallicensinginfos"));
            result.setTotalSupplier(rs.getLong("total_supplier"));
            result.setTotalPackageNames(rs.getLong("total_package_name"));
            result.setTotalPackageVersionInfo(rs.getLong("total_package_version_info"));
            result.setTotalSpdxId(rs.getLong("total_spdxid"));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private static Map<String, String> convertArrayToMap(String array[]) {
        Map<String, String> result = new HashMap<>();
        for (String pair : array) {
            String[] parts = pair.split(":");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                result.put(key, value);
            }
        }
        return result;
    }
}
