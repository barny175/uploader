package com.barnas.uploadapp.storage.local;

import com.barnas.uploadapp.storage.FileStorage;
import com.barnas.uploadapp.storage.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 08.12.2020.
 */
@Service
@Slf4j
@Profile("local")
public class LocalFileStorage implements FileStorage {

    private String folder;

    public LocalFileStorage(@Value("${local-file-storage.folder}") String folder) {
        this.folder = folder;
        checkFolderExists(folder);
    }

    private void checkFolderExists(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public void store(String id, byte[] bytes) {
        File file = getFile(id);
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(bytes);
        } catch (IOException e) {
            log.error("Cannot save file " + id, e);
            throw new StorageException("Cannot save file " + id, e);
        }
    }

    @Override
    public InputStream get(String filename) {
        File file = getFile(filename);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error("File " + filename + " not found.", e);
            throw new StorageException("File " + filename + " not found.", e);
        }
    }

    private File getFile(String filename) {
        return new File(folder, filename);
    }

    @Override
    public void remove(String filename) {
        File file = getFile(filename);
        file.delete();
    }
}
