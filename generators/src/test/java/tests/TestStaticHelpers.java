package tests;

import org.junit.jupiter.api.Test;
import org.mariuxdeangelo.masterthesis.generators.StaticHelper;

import java.io.File;
import java.util.UUID;

public class TestStaticHelpers {

    @Test
    public void testConvertSpdx2CdxWithSyft() {
        File spdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.spdx.json").getFile());
        File cdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.converteSpdx2CdxWithSyft(spdxFile, cdxFile);
        assert (cdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2SpdxWithSyft() {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.converteCdx2SpdxWithSyft(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertSpdx2CdxWithCdx() {
        File spdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.spdx.json").getFile());
        File cdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.converteSpdx2CdxWithCdx(spdxFile, cdxFile);
        assert (cdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2SpdxWithCdx() {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.converteCdx2SpdxWithCdx(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }

    @Test
    public void testConvertCdx2SpdxWithSpdx() {
        File cdxFile = new File(getClass().getClassLoader().getResource("wireguard-ui-syft-source-20230721180117.cdx.json").getFile());
        File spdxFile = StaticHelper.createNewSpdxPath(UUID.randomUUID().toString()).toFile();

        StaticHelper.converteCdx2SpdxWithSpdx(cdxFile, spdxFile);
        assert (spdxFile.exists());
        System.out.println(spdxFile);
    }
}