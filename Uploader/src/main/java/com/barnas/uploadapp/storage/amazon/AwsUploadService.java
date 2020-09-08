package com.barnas.uploadapp.storage.amazon;

import com.amazonaws.services.s3.AmazonS3;
import com.barnas.uploadapp.storage.UploadService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 04/09/2020.
 */
@Service
public class AwsUploadService implements UploadService {

    private final S3Storage fileStorage;

    @Inject
    public AwsUploadService(S3Storage s3Storage, ResourceLoader resourceLoader, AmazonS3 amazonS3) {
        this.fileStorage = s3Storage;
    }

    @Override
    public void store(String filename, byte[] bytes, String description) {
        fileStorage.store(filename, bytes);
    }
}
