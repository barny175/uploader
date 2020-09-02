package com.barnas.uploadapp.uploader;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 01/09/2020.
 */
public class UploadResponse {
    private String fileName;
    private long size;

    public UploadResponse(String fileName, long size) {
        this.fileName = fileName;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
