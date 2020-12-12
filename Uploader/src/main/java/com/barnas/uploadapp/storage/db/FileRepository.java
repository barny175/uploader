package com.barnas.uploadapp.storage.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 07/09/2020.
 */
@Repository
public interface FileRepository extends CrudRepository<FileEntity, Long> {

    Optional<FileEntity> findByFilename(String filename);
}
