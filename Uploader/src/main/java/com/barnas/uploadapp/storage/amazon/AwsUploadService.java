package com.barnas.uploadapp.storage.amazon;

import com.barnas.uploadapp.storage.FileDescriptor;
import com.barnas.uploadapp.storage.UploadService;
import com.barnas.uploadapp.storage.amazon.db.FileDescriptorMapper;
import com.barnas.uploadapp.storage.amazon.db.FileEntity;
import com.barnas.uploadapp.storage.amazon.db.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 04/09/2020.
 */
@Service
@Slf4j
public class AwsUploadService implements UploadService {

    private final S3Storage fileStorage;
    private final FileRepository fileRepository;

    @Inject
    public AwsUploadService(S3Storage s3Storage, FileRepository fileRepository) {
        this.fileStorage = s3Storage;
        this.fileRepository = fileRepository;
    }

    @Override
    public void store(String filename, byte[] bytes, String description) {
        fileStorage.store(filename, bytes);
        fileRepository.save(new FileEntity(filename, description, bytes.length));
        log.debug("File {} stored", filename);
    }

    @Override
    public List<FileDescriptor> list() {
        return StreamSupport.stream(fileRepository.findAll().spliterator(), false)
                .map(FileDescriptorMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public InputStream get(long id) {
        Optional<FileEntity> entity = fileRepository.findById(id);
        return fileStorage.get(entity.orElseThrow().getFilename());
    }
}
