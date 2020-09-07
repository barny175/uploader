package com.barnas.uploadapp.storage.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.barnas.uploadapp.storage.StorageException;
import com.barnas.uploadapp.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 04/09/2020.
 */
@Service
public class S3StorageService implements StorageService {

    public static final String BUCKET = "crossover.hw";
    private final ResourceLoader resourceLoader;
    private final AmazonS3 amazonS3;

    @Inject
    public S3StorageService(ResourceLoader resourceLoader, AmazonS3 amazonS3) {
        this.resourceLoader = resourceLoader;
        this.amazonS3 = amazonS3;
    }

    @Override
    public void store(String filename, byte[] bytes, String description) {
        Resource resource = this.resourceLoader.getResource(s3Url(filename));
        try (OutputStream outputStream = ((WritableResource) resource).getOutputStream()){
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new StorageException("Cannot upload file to AWS S3.", e);
        }
    }

    private String s3Url(String filename) {
        return "s3://" + BUCKET + "/" + filename;
    }

    @Override
    public void remove(String filename) {
        String s3Url = s3Url(filename);
        amazonS3.deleteObject(BUCKET, filename);
    }
}
