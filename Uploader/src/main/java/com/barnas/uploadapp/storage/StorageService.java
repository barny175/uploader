package com.barnas.uploadapp.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 01/09/2020.
 */
public interface StorageService {

    void store(MultipartFile file, String description);
}
