package com.barnas.uploadapp.uploader;

import java.util.List;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 09/09/2020.
 */
public class ListFilesResponse {
    private final List<FileResource> files;

    public ListFilesResponse(List<FileResource> files) {
        this.files = files;
    }

    public List<FileResource> getFiles() {
        return files;
    }
}
