package org.mariuxdeangelo.masterthesis.database.rowMapperDatabase;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SubjectProjectModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectProjectsDatabaseRowMapper implements RowMapper<SubjectProjectModel> {

    @Override
    public SubjectProjectModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubjectProjectModel result = new SubjectProjectModel();

        result.setProjectId(rs.getLong("project_Id"));
        result.setName(rs.getString("name"));
        result.setContainer(rs.getString("container"));
        result.setGit(rs.getString("git"));

        return result;
    }
}
