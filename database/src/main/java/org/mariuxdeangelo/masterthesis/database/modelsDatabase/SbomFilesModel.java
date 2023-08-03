package org.mariuxdeangelo.masterthesis.database.modelsDatabase;

import java.sql.Timestamp;

public class SbomFilesModel {
    private long projectId;
    private long sbomFileId;
    private long executionTime;
    private String generator;
    private String mode;
    private Timestamp timestamp;
    private String command;
    private byte[] shellOutput;
    private boolean orig_spdx;
    private String spdx;
    private boolean orig_cdx;
    private String cdx;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getSbomFileId() {
        return sbomFileId;
    }

    public void setSbomFileId(long sbomFileId) {
        this.sbomFileId = sbomFileId;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
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

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public byte[] getShellOutput() {
        return shellOutput;
    }

    public void setShellOutput(byte[] shellOutput) {
        this.shellOutput = shellOutput;
    }

    public boolean isOrig_spdx() {
        return orig_spdx;
    }

    public void setOrig_spdx(boolean orig_spdx) {
        this.orig_spdx = orig_spdx;
    }

    public String getSpdx() {
        return spdx;
    }

    public void setSpdx(String spdx) {
        this.spdx = spdx;
    }

    public boolean isOrig_cdx() {
        return orig_cdx;
    }

    public void setOrig_cdx(boolean orig_cdx) {
        this.orig_cdx = orig_cdx;
    }

    public String getCdx() {
        return cdx;
    }

    public void setCdx(String cdx) {
        this.cdx = cdx;
    }
}
