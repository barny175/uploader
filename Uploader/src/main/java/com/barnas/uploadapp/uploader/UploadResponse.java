package com.barnas.uploadapp.uploader;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 01/09/2020.
 */
public class UploadResponse {
    private String fileName;
    private long size;
    private long id;

    public UploadResponse(String fileName, long size, long id) {
        this.fileName = fileName;
        this.size = size;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
