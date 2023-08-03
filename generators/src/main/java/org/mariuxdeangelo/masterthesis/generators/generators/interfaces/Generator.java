package org.mariuxdeangelo.masterthesis.generators.generators.interfaces;


import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;

public interface Generator {

    /**
     * Generates the Sbom based of the initialisation of the
     * Genenerator specified in the Abstract implementations.
     * @return
     */
    SbomFilesModel generate();

    String generatorMode();

    long getProjectId();

    String generatorName();
}