package org.mariuxdeangelo.masterthesis.database;

import java.util.Properties;

public class StaticPropertyLoader {

    /**
     * First checks for an environment variable that can be set e.g. over
     * Docker to configure the application and then falls back to the build
     * in Property file.
     * @param key
     * @param properties
     * @return
     */
    public static String loadProperty(String key, Properties properties) {
        String result = System.getenv(key);

        if (result == null) {
            result = properties.getProperty(key);
        }

        return result;
    }

}
