package com.barnas.uploadapp.storage.amazon;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.barnas.uploadapp.storage.FileStorage;
import com.barnas.uploadapp.storage.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 07/09/2020.
 */
@Service
@Slf4j
@Profile("!local")
public class S3Storage implements FileStorage {

    private final ResourceLoader resourceLoader;
    private final AmazonS3 amazonS3;
    private final String bucket;

    @Inject
    public S3Storage(ResourceLoader resourceLoader, AmazonS3 amazonS3, @Value("${s3.bucket}") String bucket) {
        this.resourceLoader = resourceLoader;
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    @Override
    public void store(String id, byte[] bytes) {
        log.debug("Storing file {} to bucket {}", id, bucket);

        Resource resource = this.resourceLoader.getResource(s3Url(id));
        try (OutputStream outputStream = ((WritableResource) resource).getOutputStream()){
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new StorageException("Cannot upload file to AWS S3.", e);
        }
    }

    @Override
    public InputStream get(String filename) {
        S3Object s3Object = amazonS3.getObject(bucket, filename);
        return s3Object.getObjectContent();
    }

    private String s3Url(String filename) {
        return "s3://" + bucket + "/" + filename;
    }

    @Override
    public void remove(String filename) {
        log.debug("Deleting file {}", filename);
        amazonS3.deleteObject(bucket, filename);
    }
}
