package org.mariuxdeangelo.masterthesis.generators.generators.interfaces;

import java.nio.file.Path;

/**
 * Generates a SBOM based on a given source repository.
 * Like a GIT Repository.
 */
public abstract class GeneratorSources implements Generator {

    private final Path sources;
    private final long projectId;

    protected GeneratorSources(Path sources, long projectId) {
        this.sources = sources;
        this.projectId = projectId;
    }

    public Path getSources() {
        return sources;
    }

    public long getProjectId() {
        return projectId;
    }

    @Override
    public String generatorMode() {
        return "source";
    }
}
