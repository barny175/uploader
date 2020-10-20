package com.barnas.uploadapp.storage;

import lombok.Value;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 05/10/2020.
 */
@Value
public class FileDescriptor {
    String name;
    int size;
    String description;
    long id;
}
