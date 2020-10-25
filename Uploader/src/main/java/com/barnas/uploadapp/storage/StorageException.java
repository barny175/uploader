package com.barnas.uploadapp.storage;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 05/09/2020.
 */
public class StorageException extends RuntimeException {
    public StorageException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public StorageException(String msg) {
        super(msg);
    }
}
