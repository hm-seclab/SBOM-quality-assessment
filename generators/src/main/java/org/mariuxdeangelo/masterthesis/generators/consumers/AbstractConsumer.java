package org.mariuxdeangelo.masterthesis.generators.consumers;

import java.io.File;
import java.nio.file.Path;

public interface AbstractConsumer {

    File consumeSbom(Path sbom);

    String getConsumerName();
}
