package tests.generator;

import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;
import org.mariuxdeangelo.masterthesis.generators.generators.*;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.Generator;

public class TestGeneratorContainer extends TestAbstractGenerator {

    @Test
    public void testSyftGeneratorContainer() {
        Generator generator = new SyftGeneratorContainer(getTestContainerName(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testMicrosoftGeneratorContainer() {
        Generator generator = new MicrosoftGeneratorContainer(getTestContainerName(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testTrivyGeneratorContainer() {
        Generator generator = new TrivyGeneratorContainer(getTestContainerName(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testCycloneDxGeneratorContainer() {
        Generator generator = new CycloneDxGeneratorContainer(getTestContainerName(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

    @Test
    public void testTernGeneratorContainer() {
        Generator generator = new TernGeneratorContainer(getTestContainerName(), 0);

        SbomFilesModel output = generator.generate();
        assert output.getCdx() != null;
        assert !output.getCdx().isEmpty();
        assert output.getSpdx() != null;
        assert !output.getSpdx().isEmpty();
        assert output.getShellOutput().length > 0;
    }

}
