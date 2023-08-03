package org.mariuxdeangelo.masterthesis.database.rowMapperView;

import org.mariuxdeangelo.masterthesis.database.modelsView.DependencyListViewModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RowMapperDependencyList implements RowMapper<DependencyListViewModel> {

    @Override
    public DependencyListViewModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        DependencyListViewModel result = new DependencyListViewModel();
        result.setName(rs.getString("package_name"));
        result.setGenerator(Arrays.asList((String[]) rs.getArray("generator_list").getArray()));
        return result;
    }
}
