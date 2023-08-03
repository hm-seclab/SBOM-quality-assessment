package tests.generator;

import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.generators.*;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.Generator;

public class TestGeneratorRelease extends TestAbstractGenerator {

    @Test
    public void testSyftGeneratorrelease() {
        Generator syftGeneratorrelease = new SyftGeneratorRelease(getTestreleasePath(), 0);

        SbomFilesModel output = syftGeneratorrelease.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testMicrosoftGeneratorrelease() {
        Generator generator = new MicrosoftGeneratorRelease(getTestreleasePath(), 0);

        SbomFilesModel output = generator.generate();
//        assert output.getCdx() != null;
//        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testTrivyGeneratorrelease() {
        Generator generator = new TrivyGeneratorRelease(getTestreleasePath(), 0);

        SbomFilesModel output = generator.generate();
//        assert output.getCdx() != null;
//        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testCycloneDxGeneratorrelease() {
        Generator generator = new CycloneDxGeneratorRelease(getTestreleasePath(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }
}
