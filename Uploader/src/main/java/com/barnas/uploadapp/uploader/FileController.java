package com.barnas.uploadapp.uploader;

import com.barnas.uploadapp.storage.FileDescriptor;
import com.barnas.uploadapp.storage.FileService;
import com.barnas.uploadapp.storage.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 29/08/2020.
 */
@CrossOrigin
@RestController
@Slf4j
public class FileController {

    private final FileService fileService;

    @Inject
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public UploadResponse handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) {

        try {
            long id = fileService.store(file.getOriginalFilename(), file.getBytes(), description);
            log.debug("File {} uploaded to store.", file.getOriginalFilename());
            return new UploadResponse(file.getOriginalFilename(), file.getSize(), id);
        } catch (IOException e) {
            throw new RuntimeException("Cannot upload file.", e);
        }
    }

    @GetMapping("/list")
    public ListFilesResponse listFiles() {
        List<FileResource> files = fileService.list().stream()
                .map((FileDescriptor t) -> map(t))
                .collect(Collectors.toList());
        return new ListFilesResponse(files);
    }

    private FileResource map(FileDescriptor f) {
        String downloadPath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(String.valueOf(f.getId())).toUriString();
        return new FileResource(f.getName(), f.getDescription(), f.getSize(), f.getId(), downloadPath);
    }

    @GetMapping(
            value = "/download/{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] download(@PathVariable("id") long id) throws IOException {
        return fileService.get(id)
                .orElseThrow(() -> new StorageException("File not found."))
                .readAllBytes();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        fileService.delete(id);
    }
}
