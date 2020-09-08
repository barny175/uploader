package com.barnas.uploadapp.uploader;

import com.barnas.uploadapp.storage.UploadService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 29/08/2020.
 */
@RestController
public class UploadController {

    private final UploadService uploadService;

    @Inject
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @CrossOrigin
    @PostMapping("/upload")
    public UploadResponse handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("description") String description) {

        try {
            uploadService.store(file.getOriginalFilename(), file.getBytes(), description);
            return new UploadResponse(file.getOriginalFilename(), file.getSize());
        } catch (IOException e) {
            throw new RuntimeException("Cannot upload file.", e);
        }
    }
}
