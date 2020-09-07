package com.barnas.uploadapp.storage;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 01/09/2020.
 */
public interface StorageService {

    void store(String filename, byte[] bytes, String description);

    void remove(String file);
}
