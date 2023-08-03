package org.mariuxdeangelo.masterthesis.database.modelsView;

import java.util.Map;

public class SpdxInsightsModel {
    private Long sbomFileId;
    private String generator;
    private String mode;
    private String name;
    private String spdxId;
    private String comment;
    private String dataLicense;
    private String spdxVersion;
    private String creationDate;
    private String licenseListVersion;
    private Map<String, String> creators;
    private String documentNamespace;
    private Long total_packages;
    private Long total_relationships;
    private Long total_hasExternalLicensingInfos;
    private Long totalSupplier;
    private Long totalPackageNames;
    private Long totalPackageVersionInfo;
    private Long totalSpdxId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpdxId() {
        return spdxId;
    }

    public void setSpdxId(String spdxId) {
        this.spdxId = spdxId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDataLicense() {
        return dataLicense;
    }

    public void setDataLicense(String dataLicense) {
        this.dataLicense = dataLicense;
    }

    public String getSpdxVersion() {
        return spdxVersion;
    }

    public void setSpdxVersion(String spdxVersion) {
        this.spdxVersion = spdxVersion;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLicenseListVersion() {
        return licenseListVersion;
    }

    public void setLicenseListVersion(String licenseListVersion) {
        this.licenseListVersion = licenseListVersion;
    }

    public Map<String, String> getCreators() {
        return creators;
    }

    public void setCreators(Map<String, String> creators) {
        this.creators = creators;
    }

    public String getDocumentNamespace() {
        return documentNamespace;
    }

    public void setDocumentNamespace(String documentNamespace) {
        this.documentNamespace = documentNamespace;
    }

    public Long getTotal_packages() {
        return total_packages;
    }

    public void setTotal_packages(Long total_packages) {
        this.total_packages = total_packages;
    }

    public Long getTotal_relationships() {
        return total_relationships;
    }

    public void setTotal_relationships(Long total_relationships) {
        this.total_relationships = total_relationships;
    }

    public Long getTotal_hasExternalLicensingInfos() {
        return total_hasExternalLicensingInfos;
    }

    public void setTotal_hasExternalLicensingInfos(Long total_hasExternalLicensingInfos) {
        this.total_hasExternalLicensingInfos = total_hasExternalLicensingInfos;
    }

    public Long getSbomFileId() {
        return sbomFileId;
    }

    public void setSbomFileId(Long sbomFileId) {
        this.sbomFileId = sbomFileId;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getTotalSupplier() {
        return totalSupplier;
    }

    public void setTotalSupplier(Long totalSupplier) {
        this.totalSupplier = totalSupplier;
    }

    public Long getTotalPackageNames() {
        return totalPackageNames;
    }

    public void setTotalPackageNames(Long totalPackageNames) {
        this.totalPackageNames = totalPackageNames;
    }

    public Long getTotalPackageVersionInfo() {
        return totalPackageVersionInfo;
    }

    public void setTotalPackageVersionInfo(Long totalPackageVersionInfo) {
        this.totalPackageVersionInfo = totalPackageVersionInfo;
    }

    public Long getTotalSpdxId() {
        return totalSpdxId;
    }

    public void setTotalSpdxId(Long totalSpdxId) {
        this.totalSpdxId = totalSpdxId;
    }
}
