package com.barnas.uploadapp.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 01/09/2020.
 */
@Service
public class FakeStorage implements StorageService {
    @Override
    public void store(MultipartFile file) {
        System.out.println("Storing file " + file.getOriginalFilename());
    }
}
