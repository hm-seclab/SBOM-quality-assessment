package org.mariuxdeangelo.masterthesis.database.rowMapperDatabase;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.ConsumerModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumerDatabaseRowMapper implements RowMapper<ConsumerModel> {

    @Override
    public ConsumerModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsumerModel result = new ConsumerModel();

        result.setConsummerId(rs.getLong("consumer_id"));
        result.setSbomFileId(rs.getLong("sbom_file_id"));
        result.setConsumer(rs.getString("consumer"));
        result.setType(rs.getString("type"));
        result.setTimestamp(rs.getTimestamp("timestamp"));
        result.setReport(rs.getString("report"));

        return result;
    }
}
