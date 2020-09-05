package com.barnas.uploadapp.storage.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.barnas.uploadapp.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 04/09/2020.
 */
@Service
public class S3StorageService implements StorageService {

    private final ResourceLoader resourceLoader;

    @Inject
    public S3StorageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void store(MultipartFile file, String description) {
        Resource resource = this.resourceLoader.getResource("s3://crossover.hw/" + file.getOriginalFilename());
        try (OutputStream outputStream = ((WritableResource) resource).getOutputStream()){
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace(); // TODO
        }
    }
}
