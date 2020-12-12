package com.barnas.uploadapp.storage;

import java.io.InputStream;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 08.12.2020.
 */
public interface FileStorage {
    void store(String id, byte[] bytes);

    InputStream get(String filename);

    void remove(String filename);
}
