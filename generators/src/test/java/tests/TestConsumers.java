package tests;

import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.generators.consumers.AbstractConsumer;
import org.mariuxdeangelo.masterthesis.generators.consumers.ConsumerGrype;
import org.mariuxdeangelo.masterthesis.generators.consumers.ConsumerOsvDev;
import org.mariuxdeangelo.masterthesis.generators.generators.SyftGeneratorContainer;
import org.mariuxdeangelo.masterthesis.generators.generators.interfaces.Generator;
import tests.generator.TestAbstractGenerator;

import java.io.File;

public class TestConsumers extends TestAbstractGenerator {

    @Test
    public void testConsumerGrype() {
//        Generator generator = new SyftGeneratorContainer(getTestContainerName());
//        GeneratorResult sbom = generator.generate();
//
//        AbstractConsumer consumer = new ConsumerGrype();
//        File file = consumer.consumeSbom(sbom.getSpdx());
//
//        assert(file.length() != 0);
//        assert(file.exists());
//        assert(file.canRead());
//        file.delete();
    }

    @Test
    public void testConsumerOsvDev() {
//        Generator generator = new SyftGeneratorContainer(getTestContainerName());
//        GeneratorResult sbom = generator.generate();
//
//        AbstractConsumer consumer = new ConsumerOsvDev();
//        File file = consumer.consumeSbom(sbom.getSpdx());
//
//        assert(file.length() != 0);
//        assert(file.exists());
//        assert(file.canRead());
//        file.delete();
    }

}
