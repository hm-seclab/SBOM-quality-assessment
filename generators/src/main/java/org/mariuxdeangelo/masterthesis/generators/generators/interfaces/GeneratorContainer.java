package org.mariuxdeangelo.masterthesis.generators.generators.interfaces;

import java.nio.file.Path;

/**
 * Generates SBOMs based on a given Container image.
 */
public abstract class GeneratorContainer implements Generator {

    private final String container;
    private final long projectId;

    protected GeneratorContainer(String container, long projectId) {
        this.container = container;
        this.projectId = projectId;
    }

    public String getContainer() {
        return container;
    }

    public long getProjectId() {
        return projectId;
    }

    @Override
    public String generatorMode() {
        return "container";
    }

    public static void main(String args[]) {
        String test = String.format("Das ist ein test%s hier", "test");
        System.out.println(test);
    }
}
