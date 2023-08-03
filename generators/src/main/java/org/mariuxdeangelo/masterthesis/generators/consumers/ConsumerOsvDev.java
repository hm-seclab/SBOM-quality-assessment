package org.mariuxdeangelo.masterthesis.generators.consumers;

import org.mariuxdeangelo.masterthesis.generators.CommandExecuter;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class ConsumerOsvDev implements AbstractConsumer {

    @Override
    public File consumeSbom(Path sbom) {
        String uuid = UUID.randomUUID().toString();
        Path output = StaticHelper.createNewOutputPath(getConsumerName(), uuid);
        Path castOutput = StaticHelper.createNewOutputPathCast(getConsumerName(), uuid);
        String command = "osv-scanner --sbom %s --format json";
        CommandExecuter.executeRecorded(String.format(command, sbom), castOutput);

        return output.toFile();
    }

    @Override
    public String getConsumerName() {
        return "osvdev";
    }
}
