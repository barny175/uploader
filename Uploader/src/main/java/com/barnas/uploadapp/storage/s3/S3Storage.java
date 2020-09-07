package com.barnas.uploadapp.storage.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.barnas.uploadapp.storage.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 07/09/2020.
 */
@Service
public class S3Storage {

    public static final String BUCKET = "crossover.hw";
    private final ResourceLoader resourceLoader;
    private final AmazonS3 amazonS3;

    @Inject
    public S3Storage(ResourceLoader resourceLoader, AmazonS3 amazonS3) {
        this.resourceLoader = resourceLoader;
        this.amazonS3 = amazonS3;
    }

    public void store(String filename, byte[] bytes) {
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

    public void remove(String filename) {
        amazonS3.deleteObject(BUCKET, filename);
    }
}
