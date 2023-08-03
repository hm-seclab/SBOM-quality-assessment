package org.mariuxdeangelo.masterthesis.database.rowMapperDatabase;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SbomFilesDatabaseRowMapper implements RowMapper<SbomFilesModel> {

    @Override
    public SbomFilesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SbomFilesModel result = new SbomFilesModel();
        List<String> columnNameList = getColumnNameList(rs);

        result.setSbomFileId(rs.getLong("sbom_file_id"));
        result.setProjectId(rs.getLong("project_id"));
        result.setGenerator(rs.getString("generator"));
        result.setMode(rs.getString("mode"));
        result.setTimestamp(rs.getTimestamp("timestamp"));

        if (columnNameList.contains("spdx"))
            result.setSpdx(rs.getString("spdx"));
        if (columnNameList.contains("cdx"))
            result.setCdx(rs.getString("cdx"));
        if (columnNameList.contains("spdx_orig"))
            result.setOrig_spdx(rs.getBoolean("spdx_orig"));
        if (columnNameList.contains("cdx_orig"))
            result.setOrig_cdx(rs.getBoolean("cdx_orig"));

        return result;
    }

    public static List<String> getColumnNameList(ResultSet rs) {
        List<String> columNameList = new ArrayList<>();

        try {
            int columnCount = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                columNameList.add(rs.getMetaData().getColumnName(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columNameList;
    }
}
