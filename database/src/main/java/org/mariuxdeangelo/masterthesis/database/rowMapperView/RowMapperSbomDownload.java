package org.mariuxdeangelo.masterthesis.database.rowMapperView;

import org.mariuxdeangelo.masterthesis.database.modelsView.SbomDownloadViewModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperSbomDownload implements RowMapper<SbomDownloadViewModel> {

    @Override
    public SbomDownloadViewModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SbomDownloadViewModel result = new SbomDownloadViewModel();

        result.setProject_id(rs.getLong("project_id"));
        result.setSbom_file_id(rs.getLong("sbom_file_id"));
        result.setName(rs.getString("name"));
        result.setGenerator(rs.getString("generator"));
        result.setMode(rs.getString("mode"));
        result.setTimestamp(rs.getTimestamp("timestamp"));
        result.setSpdx_orig(rs.getBoolean("spdx_orig"));
        result.setCdx_orig(rs.getBoolean("cdx_orig"));
        result.setSpdx(rs.getString("spdx"));
        result.setCdx(rs.getString("cdx"));
        result.setCommand(rs.getString("command"));
        result.setShellLog(rs.getBytes("shell_out"));

        return result;
    }
}
