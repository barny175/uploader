package com.barnas.uploadapp.storage.amazon;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 05/09/2020.
 */
@SpringBootTest()
public class S3StorageTest {
    public static final String CONTENT = "this content of the file in s3";
    public static final String TEST_FILE = "test.txt";

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private S3Storage s3Storage;

    @Test
    public void uploadFileToS3() {
        String filename = "test.txt";
        s3Storage.store(filename, CONTENT.getBytes());
        try (S3Object object = amazonS3.getObject("crossover.hw", filename)) {
            String fileContent = new String(object.getObjectContent().readAllBytes());
            Assertions.assertEquals(CONTENT, fileContent);
        } catch (IOException e) {
            Assertions.fail(e);
        } finally {
            s3Storage.remove(filename);
        }
    }

    @Test
    public void removeFileFromS3() {
        String filename = TEST_FILE;
        s3Storage.store(filename, CONTENT.getBytes());
        s3Storage.remove(filename);
        assertThat(amazonS3.doesObjectExist("crossover.hw", filename)).isFalse();
    }
}