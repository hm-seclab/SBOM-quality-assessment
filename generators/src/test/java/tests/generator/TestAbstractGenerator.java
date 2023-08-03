package tests.generator;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.UUID;

public class TestAbstractGenerator {
    private final String testContainerName;
    private final Path testreleasePath;
    private final Path testSourcesPath;
    private final Path testWorkDir;
    private final String testGitUrl;
    private final String testGitName;

    public TestAbstractGenerator() {
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("generatorTestConfig.properties");
        Properties properties = new Properties();

        try {
            properties.load(configStream);
            configStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        testContainerName = properties.getProperty("test.container.name");
        testSourcesPath = Path.of(properties.getProperty("test.sources"));
        testreleasePath = Path.of(properties.getProperty("test.repository"));
        testWorkDir = Path.of(properties.getProperty("test.workDir"));
        testGitUrl = properties.getProperty("test.git.url");
        testGitName = properties.getProperty("test.git.name");
    }

    public String getTestContainerName() {
        return testContainerName;
    }

    public Path getTestreleasePath() {
        return testreleasePath;
    }

    public Path getTestSourcesPath() {
        return testSourcesPath;
    }

    public Path getTestWorkDir() {
        return testWorkDir;
    }

    public String getTestGitUrl() {
        return testGitUrl;
    }

    public String getTestGitName() {
        return testGitName;
    }

    public Path getTestSbomLocation() {
        return testWorkDir.resolve(UUID.randomUUID().toString() + ".spdx.json");
    }
}
