package com.barnas.uploadapp.storage.amazon;

import com.barnas.uploadapp.storage.FileDescriptor;
import com.barnas.uploadapp.storage.amazon.db.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 19/10/2020.
 */
@SpringBootTest
class AwsFileServiceTest {

    @Inject
    S3Storage fileStorage;

    @Inject
    FileRepository fileRepository;

    private AwsFileService awsFileService;

    @BeforeEach
    public void setup() {
        this.awsFileService = new AwsFileService(fileStorage, fileRepository);
    }

    @Test
    public void store() {
        String filename = "test.file" + new Random().nextInt();
        try {
            awsFileService.store(filename, "content".getBytes(), "test file");

            assertThat(fileRepository.findByFilename(filename))
                    .isPresent();

            assertThat(awsFileService.list())
                    .extracting(FileDescriptor::getName, FileDescriptor::getDescription)
                    .contains(tuple(filename, "test file"));
        } finally {
            fileRepository.findByFilename(filename)
                    .ifPresent(fe -> fileRepository.delete(fe));
            fileStorage.remove(filename);
        }
    }
}