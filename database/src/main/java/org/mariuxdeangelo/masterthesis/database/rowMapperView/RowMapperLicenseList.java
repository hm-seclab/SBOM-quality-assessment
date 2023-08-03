package org.mariuxdeangelo.masterthesis.database.rowMapperView;

import org.mariuxdeangelo.masterthesis.database.modelsView.LicenseListViewModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RowMapperLicenseList implements RowMapper<LicenseListViewModel> {

    @Override
    public LicenseListViewModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        LicenseListViewModel result = new LicenseListViewModel();

        result.setAmount(rs.getLong("amount"));
        result.setLicense(rs.getString("license"));
        result.setGenerator(rs.getString("generator"));
        result.setPackageList(Arrays.asList((String[]) rs.getArray("package_list").getArray()));

        return result;
    }
}
