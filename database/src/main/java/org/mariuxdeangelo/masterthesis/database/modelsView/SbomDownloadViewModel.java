package org.mariuxdeangelo.masterthesis.database.modelsView;

import java.sql.Timestamp;

public class SbomDownloadViewModel {
    private long project_id;
    private long sbom_file_id;
    private String name;
    private String generator;
    private String mode;
    private Timestamp timestamp;
    private boolean spdx_orig;
    private boolean cdx_orig;
    private String spdx;
    private String cdx;
    private String command;
    private byte[] shellLog;

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getSbom_file_id() {
        return sbom_file_id;
    }

    public void setSbom_file_id(long sbom_file_id) {
        this.sbom_file_id = sbom_file_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isSpdx_orig() {
        return spdx_orig;
    }

    public void setSpdx_orig(boolean spdx_orig) {
        this.spdx_orig = spdx_orig;
    }

    public boolean isCdx_orig() {
        return cdx_orig;
    }

    public void setCdx_orig(boolean cdx_orig) {
        this.cdx_orig = cdx_orig;
    }

    public String getSpdx() {
        return spdx;
    }

    public void setSpdx(String spdx) {
        this.spdx = spdx;
    }

    public String getCdx() {
        return cdx;
    }

    public void setCdx(String cdx) {
        this.cdx = cdx;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public byte[] getShellLog() {
        return shellLog;
    }

    public void setShellLog(byte[] shellLog) {
        this.shellLog = shellLog;
    }
}
