package org.mariuxdeangelo.masterthesis.database.modelsView;

import java.sql.Timestamp;

public class SbomMetadataViewModel {
    private long project_id;
    private String name;
    private String container;
    private String git;
    private long sbom_file_id;
    private String generator;
    private String mode;
    private Timestamp timestamp;
    private boolean cdx_orig;
    private boolean cdx_exists;
    private boolean spdx_orig;
    private boolean spdx_exists;
    private String command;
    private boolean log_exists;
    private long executionTime;

    private int totalPackages;
    private int totalRelationships;
    private int totalExternalLicenseInformation;

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public long getSbom_file_id() {
        return sbom_file_id;
    }

    public void setSbom_file_id(long sbom_file_id) {
        this.sbom_file_id = sbom_file_id;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isCdx_orig() {
        return cdx_orig;
    }

    public void setCdx_orig(boolean cdx_orig) {
        this.cdx_orig = cdx_orig;
    }

    public boolean isCdx_exists() {
        return cdx_exists;
    }

    public void setCdx_exists(boolean cdx_exists) {
        this.cdx_exists = cdx_exists;
    }

    public boolean isSpdx_orig() {
        return spdx_orig;
    }

    public void setSpdx_orig(boolean spdx_orig) {
        this.spdx_orig = spdx_orig;
    }

    public boolean isSpdx_exists() {
        return spdx_exists;
    }

    public void setSpdx_exists(boolean spdx_exists) {
        this.spdx_exists = spdx_exists;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isLog_exists() {
        return log_exists;
    }

    public void setLog_exists(boolean log_exists) {
        this.log_exists = log_exists;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public int getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(int totalPackages) {
        this.totalPackages = totalPackages;
    }

    public int getTotalRelationships() {
        return totalRelationships;
    }

    public void setTotalRelationships(int totalRelationships) {
        this.totalRelationships = totalRelationships;
    }

    public int getTotalExternalLicenseInformation() {
        return totalExternalLicenseInformation;
    }

    public void setTotalExternalLicenseInformation(int totalExternalLicenseInformation) {
        this.totalExternalLicenseInformation = totalExternalLicenseInformation;
    }
}
