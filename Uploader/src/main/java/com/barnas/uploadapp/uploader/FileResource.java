package com.barnas.uploadapp.uploader;

import lombok.Value;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 09/09/2020.
 */
@Value
public class FileResource {
    private final String filename;
    private final String description;
    private final int size;
    private final Long id;
    private final String downloadPath;
}
