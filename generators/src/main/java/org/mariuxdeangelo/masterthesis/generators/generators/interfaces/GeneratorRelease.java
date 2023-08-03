package org.mariuxdeangelo.masterthesis.generators.generators.interfaces;

import java.nio.file.Path;

/**
 * Generator Interfaces for Generation of SBOMs
 * based on the build output of a project. Like the Release
 * information of a repository or installer files like .deb
 * or tar-balls.
 */
public abstract class GeneratorRelease implements Generator {

    private final Path repository;
    private final long projectId;

    protected GeneratorRelease(Path repository, long projectId){
        this.repository = repository;
        this.projectId = projectId;
    }

    public Path getRepository() {
        return repository;
    }

    public long getProjectId() {
        return projectId;
    }

    @Override
    public String generatorMode() {
        return "release";
    }
}
