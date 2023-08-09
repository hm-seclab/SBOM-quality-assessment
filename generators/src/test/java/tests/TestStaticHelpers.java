package tests;

import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TestStaticHelpers {

    @Test
    public void testConvertSpdx2Cdx() throws IOException {
        File spdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.spdx.json").getFile());
        File cdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertSpdx2Cdx(spdxFile, cdxFile);
        assert (cdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2Spdx() throws IOException {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertCdx2Spdx(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertSpdx2CdxWithSyft() {
        File spdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.spdx.json").getFile());
        File cdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertSpdx2CdxWithSyft(spdxFile, cdxFile);
        assert (cdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2SpdxWithSyft() {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertCdx2SpdxWithSyft(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertSpdx2CdxWithCdx() {
        File spdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-cdxgen-container-20230721180006.spdx.json").getFile());
        File cdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertSpdx2CdxWithCdx(spdxFile, cdxFile);
        assert (cdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2SpdxWithCdx() {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-cdxgen-container-20230721180006.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertCdx2SpdxWithCdx(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2SpdxWithSpdx() {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertCdx2SpdxWithSpdx(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

    public static void main(String args[]) {
        File cdxFile = new File("/home/mariuxdeangelo/Downloads/ruby-cdxgen-source-20230805102613.cdx.json");
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.convertCdx2SpdxWithCdx(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

//    public static void main(String args[]) {
//        File spdxFile = new File("/home/mariuxdeangelo/Downloads/debian-microsoft-container-20230805100451.spdx.json");
//        File cdxFile = StaticHelper.createNewCdxPath(UUID.randomUUID().toString()).toFile();
//
//        StaticHelper.convertSpdx2CdxWithCdx(spdxFile, cdxFile);
//        assert (cdxFile.exists());
//        System.out.println(cdxFile);
//    }
}