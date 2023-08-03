package org.mariuxdeangelo.masterthesis.database;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseAppConfig {

    // Static part of the Class

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAppConfig.class);

    private static final DatabaseAppConfig staticAppConfig = new DatabaseAppConfig();

    public static String getDbUser() {
        return staticAppConfig.dbUser;
    }

    public static String getDbPassword() {
        return staticAppConfig.dbPassword;
    }

    public static String getDbUrl() {
        return staticAppConfig.dbUrl;
    }


    // Object part of the Class

    private final String dbUser;
    private final String dbPassword;
    private final String dbUrl;

    public DatabaseAppConfig() {
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();

        try {
            properties.load(configStream);
            configStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbUser = StaticPropertyLoader.loadProperty("DB_USER", properties);
        dbPassword = StaticPropertyLoader.loadProperty("DB_PASSWORD", properties);
        dbUrl = StaticPropertyLoader.loadProperty("DB_URL", properties);

        logger.info("DB_USER {}", dbUser);
        logger.info("DB_URL {}", dbUrl);
    }
}
