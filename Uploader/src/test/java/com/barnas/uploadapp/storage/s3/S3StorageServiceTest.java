package com.barnas.uploadapp.storage.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;

import java.io.IOException;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 05/09/2020.
 */
@SpringBootTest()
public class S3StorageServiceTest {
    public static final String CONTENT = "this content of the file in s3";

    @Autowired
    private S3StorageService storageService;

    @Autowired
    private AmazonS3 amazonS3;

    @Rule
    public LocalStackContainer localStack = new LocalStackContainer()
            .withServices(LocalStackContainer.Service.S3)
            .withEnv("DEFAULT_REGION", "eu-west-1");

    @Test
    public void uploadFileToS3() {
        storageService.store("test.txt", CONTENT.getBytes(), "test file");
        S3Object object = amazonS3.getObject("crossover.hw", "test.txt");
        String fileContent = null;
        try {
            fileContent = new String(object.getObjectContent().readAllBytes());
            Assertions.assertEquals(CONTENT, fileContent);
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }
}