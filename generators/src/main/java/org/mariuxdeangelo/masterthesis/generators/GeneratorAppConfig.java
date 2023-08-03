package org.mariuxdeangelo.masterthesis.generators;

import org.mariuxdeangelo.masterthesis.database.StaticPropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class GeneratorAppConfig {

    // Static part of the Class

    private static final Logger logger = LoggerFactory.getLogger(GeneratorAppConfig.class);

    private static final GeneratorAppConfig staticAppConfig = new GeneratorAppConfig();

    public static Path getBaseDir() {
        return staticAppConfig.baseDir;
    }

    public static Integer getThreadpoolSize() {
        return staticAppConfig.threadpoolSize;
    }

    public static Integer getDefaultTimeout() {
        return staticAppConfig.defaultTimeout;
    }

    public static String getGitToken() {
        return staticAppConfig.gitToken;
    }

    public static Boolean isRunConsumerAfterGenerator() {
        return staticAppConfig.runConsumerAfterGenerator;
    }

    public static Boolean isRunGenerator() {
        return staticAppConfig.runGenerator;
    }

    public static Boolean isRunConsumer() {
        return staticAppConfig.runConsumer;
    }

    public static Boolean isRunRegenerateExistingSbom() {
        return staticAppConfig.regenerateExistingSbom;
    }

    public static Boolean isRegenerateExistingConsumer() {
        return staticAppConfig.regenerateExistingConsumer;
    }

    public static String getGeneratorListrelease() {
        return  staticAppConfig.generatorListrelease;
    }

    public static String getGeneratorListContainer() {
        return staticAppConfig.generatorListContainer;
    }

    public static String getGeneratorListSource() {
        return staticAppConfig.generatorListSource;
    }

    // Object part of the Class

    private final Path baseDir;
    private final Integer threadpoolSize;
    private final Integer defaultTimeout;
    private final String gitToken;

    private final Boolean runConsumerAfterGenerator;
    private final Boolean runGenerator;
    private final Boolean runConsumer;
    private final Boolean regenerateExistingSbom;
    private final Boolean regenerateExistingConsumer;

    private final String generatorListrelease;
    private final String generatorListContainer;
    private final String generatorListSource;

    public GeneratorAppConfig() {
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("generator.properties");
        Properties properties = new Properties();

        try {
            properties.load(configStream);
            configStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        baseDir = Path.of(StaticPropertyLoader.loadProperty("GENERATOR_BASEDIR", properties));
        threadpoolSize = Integer.valueOf(StaticPropertyLoader.loadProperty("THREADPOOL_SIZE", properties));
        defaultTimeout = Integer.valueOf(StaticPropertyLoader.loadProperty("DEFAULT_TIMEOUT", properties));
        gitToken = StaticPropertyLoader.loadProperty("GIT_TOKEN", properties);
        runConsumerAfterGenerator = Boolean.valueOf(StaticPropertyLoader.loadProperty("RUN_CONSUMER_AFTER_GENERATOR", properties));
        runGenerator = Boolean.valueOf(StaticPropertyLoader.loadProperty("RUN_GENERATOR", properties));
        runConsumer = Boolean.valueOf(StaticPropertyLoader.loadProperty("RUN_CONSUMER", properties));
        regenerateExistingSbom = Boolean.valueOf(StaticPropertyLoader.loadProperty("REGENERATE_EXISTING_SBOM", properties));
        regenerateExistingConsumer = Boolean.valueOf(StaticPropertyLoader.loadProperty("REGENERATE_EXISTING_CONSUMER", properties));

        generatorListrelease = StaticPropertyLoader.loadProperty("GENERATOR_LIST_RELEASE", properties);
        generatorListContainer = StaticPropertyLoader.loadProperty("GENERATOR_LIST_CONTAINER", properties);
        generatorListSource = StaticPropertyLoader.loadProperty("GENERATOR_LIST_SOURCE", properties);

        logger.info("GENERATOR_BASEDIR {}", baseDir);
        logger.info("THREADPOOL_SIZE {}", threadpoolSize);
        logger.info("DEFAULT_TIMEOUT {}", defaultTimeout);
        logger.info("RUN_CONSUMER_AFTER_GENERATOR {}", runConsumerAfterGenerator);
        logger.info("RUN_GENERATOR {}", runGenerator);
        logger.info("RUN_CONSUMER {}", runConsumer);
        logger.info("REGENERATE_EXISTING_SBOM {}", regenerateExistingSbom);
        logger.info("REGENERATE_EXISTING_CONSUMER {}", regenerateExistingConsumer);

        logger.info("GENERATOR_LIST_RELEASE {}", generatorListrelease);
        logger.info("GENERATOR_LIST_CONTAINER {}", generatorListContainer);
        logger.info("GENERATOR_LIST_SOURCE {}", generatorListSource);
    }
}
