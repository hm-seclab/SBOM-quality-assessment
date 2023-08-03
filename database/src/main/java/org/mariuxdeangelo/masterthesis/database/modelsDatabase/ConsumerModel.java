package org.mariuxdeangelo.masterthesis.database.modelsDatabase;

import java.sql.Timestamp;

public class ConsumerModel {
    private long consummerId;
    private long sbomFileId;
    private String consumer;
    private String type;
    private Timestamp timestamp;
    private String command;
    private byte[] shell_output;
    private String report;

    public long getConsummerId() {
        return consummerId;
    }

    public void setConsummerId(long consummerId) {
        this.consummerId = consummerId;
    }

    public long getSbomFileId() {
        return sbomFileId;
    }

    public void setSbomFileId(long sbomFileId) {
        this.sbomFileId = sbomFileId;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public byte[] getShell_output() {
        return shell_output;
    }

    public void setShell_output(byte[] shell_output) {
        this.shell_output = shell_output;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
