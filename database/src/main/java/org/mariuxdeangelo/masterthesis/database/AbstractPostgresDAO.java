package org.mariuxdeangelo.masterthesis.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public abstract class AbstractPostgresDAO {
    private static DriverManagerDataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    public AbstractPostgresDAO() {
        if (dataSource == null) {
            dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(DatabaseAppConfig.getDbUrl());
            dataSource.setUsername(DatabaseAppConfig.getDbUser());
            dataSource.setPassword(DatabaseAppConfig.getDbPassword());
        }

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
