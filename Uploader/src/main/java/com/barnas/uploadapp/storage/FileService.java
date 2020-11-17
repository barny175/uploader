package com.barnas.uploadapp.storage;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 01/09/2020.
 */
public interface FileService {

    long store(String fileName, byte[] bytes, String description);

    List<FileDescriptor> list();

    Optional<InputStream> get(long id);

    void delete(long id);
}
