package tests.generator;

import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.generators.*;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.Generator;

public class TestGeneratorSources extends TestAbstractGenerator {

    @Test
    public void testSyftGeneratorSources() {
        Generator generator = new SyftGeneratorSources(getTestSourcesPath(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testMicrosoftGeneratorSources() {
        Generator generator = new MicrosoftGeneratorSources(getTestSourcesPath(), 0);

        SbomFilesModel output = generator.generate();
//        assert output.getCdx() != null;
//        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testTrivyGeneratorSources() {
        Generator generator = new TrivyGeneratorSources(getTestSourcesPath(), 0);

        SbomFilesModel output = generator.generate();
//        assert output.getCdx() != null;
//        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testCycloneDxGeneratorSources() {
        Generator generator = new CycloneDxGeneratorSources(getTestSourcesPath(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testGithubGeneratorSources() {
        Generator generator = new GithubGeneratorSources(getTestGitUrl(), 0);

        SbomFilesModel output = generator.generate();
//        assert output.getCdx() != null;
//        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
//        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testScanCodeGeneratorSources() {
        Generator generator = new ScanCodeGeneratorSources(getTestSourcesPath(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }
}
