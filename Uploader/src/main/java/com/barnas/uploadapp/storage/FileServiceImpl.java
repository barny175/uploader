package com.barnas.uploadapp.storage;

import com.barnas.uploadapp.storage.db.FileDescriptorMapper;
import com.barnas.uploadapp.storage.db.FileEntity;
import com.barnas.uploadapp.storage.db.FileRepository;
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
public class FileServiceImpl implements FileService {

    private final FileStorage fileStorage;
    private final FileRepository fileRepository;

    @Inject
    public FileServiceImpl(FileStorage s3Storage, FileRepository fileRepository) {
        this.fileStorage = s3Storage;
        this.fileRepository = fileRepository;
    }

    @Override
    public long store(String fileName, byte[] bytes, String description) {
        FileEntity entity = fileRepository.save(new FileEntity(fileName, description, bytes.length));
        fileStorage.store(entity.getId().toString(), bytes);
        log.debug("File {} stored with id {}", fileName, entity.getId());
        return entity.getId();
    }

    @Override
    public List<FileDescriptor> list() {
        return StreamSupport.stream(fileRepository.findAll().spliterator(), false)
                .map(FileDescriptorMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InputStream> get(long id) {
        Optional<FileEntity> entity = fileRepository.findById(id);
        return entity.map(e -> fileStorage.get(String.valueOf(id)));
    }

    @Override
    public void delete(long id) {
        FileEntity entity = fileRepository.findById(id)
            .orElseThrow(() -> new StorageException("Could not find entity " + id));

        log.debug("Removing file {} ({})", entity.getFilename(), id);
        fileRepository.deleteById(id);
        fileStorage.remove(String.valueOf(id));
    }
}
