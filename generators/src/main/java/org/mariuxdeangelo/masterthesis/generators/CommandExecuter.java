package org.mariuxdeangelo.masterthesis.generators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class CommandExecuter {

    private static final Logger logger = LoggerFactory.getLogger(CommandExecuter.class);

    public static long executeRecorded(String command, Path castOutput) {
        return executeRecorded(command, castOutput, GeneratorAppConfig.getDefaultTimeout());
    }

    public static long executeRecorded(String command, Path castOutput, int timeout) {
        logger.info("Outputing protocoll to: {}", castOutput);
//        String wrappedCommand = " xvfb-run --auto-servernum --server-args='-screen 0 1280x720x24' terminator --command=\"asciinema rec --stdin -c=' echo Generator started with command: " + command + " && " + command + " && echo Generator finished ' " + castOutput + "\"";
        String wrappedCommand = " asciinema rec --stdin -c=' echo Generator started with command: \"" + command + "\" && " + command + " && echo Generator finished ' " + castOutput;
        return execute(wrappedCommand, timeout);
    }

    /**
     * Executes a given command and ignores the output
     * or success of the command. Default timeout set by global configuration.
     * The output of the command is recorded by asciinema.
     */
    public static long execute(String command) {
        return execute(command, GeneratorAppConfig.getDefaultTimeout());
    }

    /**
     * Executes a given command and ignores the output
     * or success of the command. Timeout can be configured in seconds.
     * The output of the command is recorded by asciinema.
     */
    public static long execute(String command, int timeout) {
        long startTime = System.currentTimeMillis();
        try {
            logger.info("Executing the following command: {}", command);

            ProcessBuilder builder = new ProcessBuilder("sh", "-c", command);
            Process process = builder.start();
            boolean processResult = process.waitFor(timeout, TimeUnit.SECONDS);

            if (!processResult) {
                process.destroyForcibly();
                logger.error("The Process with the command '{}' timed ({}) out and was destroyed", command, timeout);
                return -1; // Convention for a Timeout
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
