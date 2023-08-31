package org.mariuxdeangelo.masterthesis.generators;

import org.mariuxdeangelo.masterthesis.database.modelsDatabase.SbomFilesModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class SbomDataUpdater {

    public static void main(String args[]) {
        List<SbomFilesModel> sbomFilesLite = StaticHelper.getDatabaseTableDAO().retrieveListOfSbomFilesLite();

        for (SbomFilesModel sbom : sbomFilesLite) {
            if (!sbom.isOrig_spdx() && sbom.getGenerator().equals("scancode")) {
                System.out.println("To Convert: " + sbom.getGenerator() + " " + sbom.getMode());
                SbomFilesModel fullSbom = StaticHelper.getDatabaseTableDAO().retrieveSbomFile(sbom.getSbomFileId());
                if (fullSbom.getCdx() != null) {
                    String uuid = UUID.randomUUID().toString();
                    Path tmpFile = Path.of("/home/mariuxdeangelo/Desktop/tmp/" + uuid + ".cdx.json");
                    Path resFile = Path.of("/home/mariuxdeangelo/Desktop/tmp/" + uuid + ".spdx.json");

                    try {
                        Files.write(tmpFile, fullSbom.getCdx().getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    StaticHelper.convertCdx2SpdxWithSyft(tmpFile.toFile(), resFile.toFile());
                    fullSbom.setSpdx(StaticHelper.readJsonFileToString(resFile.toFile()));
                    System.out.println(StaticHelper.getDatabaseTableDAO().insertSbomFile(fullSbom));
                }
            } else {
                System.out.println("Skipped: " + sbom.getGenerator() + " " + sbom.getMode());
            }
        }
    }

//    public static void main(String args[]) {
//        List<SbomFilesModel> sbomFilesLite = StaticHelper.getDatabaseTableDAO().retrieveListOfSbomFilesLite();
//
//        for (SbomFilesModel sbom : sbomFilesLite) {
//            if (!sbom.isOrig_cdx() && sbom.getGenerator().equals("github")) {
//                System.out.println("To Convert: " + sbom.getGenerator() + " " + sbom.getMode());
//                SbomFilesModel fullSbom = StaticHelper.getDatabaseTableDAO().retrieveSbomFile(sbom.getSbomFileId());
//                if (fullSbom.getSpdx() != null) {
//                    String uuid = UUID.randomUUID().toString();
//                    Path tmpFile = Path.of("/home/mariuxdeangelo/Desktop/tmp/" + uuid + ".spdx.json");
//                    Path resFile = Path.of("/home/mariuxdeangelo/Desktop/tmp/" + uuid + ".cdx.json");
//
//                    try {
//                        Files.write(tmpFile, fullSbom.getCdx().getBytes(StandardCharsets.UTF_8));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    StaticHelper.convertSpdx2CdxWithSyft(tmpFile.toFile(), resFile.toFile());
//                    fullSbom.setCdx(StaticHelper.readJsonFileToString(resFile.toFile()));
//                    System.out.println(StaticHelper.getDatabaseTableDAO().insertSbomFile(fullSbom));
//                }
//            } else {
//                System.out.println("Skipped: " + sbom.getGenerator() + " " + sbom.getMode());
//            }
//        }
//    }
}
