package org.mariuxdeangelo.masterthesis.database.rowMapperView;

import org.mariuxdeangelo.masterthesis.database.modelsView.SbomMetadataViewModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperSbomMetadata implements RowMapper<SbomMetadataViewModel> {

    @Override
    public SbomMetadataViewModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SbomMetadataViewModel result = new SbomMetadataViewModel();
        result.setProject_id(rs.getLong("project_id"));
        result.setName(rs.getString("name"));
        result.setContainer(rs.getString("container"));
        result.setGit(rs.getString("git"));
        result.setSbom_file_id(rs.getLong("sbom_file_id"));
        result.setGenerator(rs.getString("generator"));
        result.setMode(rs.getString("mode"));
        result.setTimestamp(rs.getTimestamp("timestamp"));
        result.setCdx_orig(rs.getBoolean("cdx_orig"));
        result.setCdx_exists(rs.getBoolean("cdx_exists"));
        result.setSpdx_orig(rs.getBoolean("spdx_orig"));
        result.setSpdx_exists(rs.getBoolean("spdx_exists"));
        result.setCommand(rs.getString("command"));
        result.setLog_exists(rs.getBoolean("log_exists"));
        result.setExecutionTime(rs.getLong("execution_time"));
        result.setTotalPackages(rs.getInt("total_packages"));
        result.setTotalRelationships(rs.getInt("total_relationships"));
        result.setTotalExternalLicenseInformation(rs.getInt("total_hasexternallicensinginfos"));
        return result;
    }
}
