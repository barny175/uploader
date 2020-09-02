package com.barnas.uploadapp.uploader;

import com.barnas.uploadapp.storage.StorageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * @author Martin Barnas (martin.barnas@avast.com)
 * @since 29/08/2020.
 */
@RestController
public class UploadController {

    private final StorageService storageService;

    @Inject
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @CrossOrigin
    @PostMapping("/upload")
    public UploadResponse handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);

        return new UploadResponse(file.getOriginalFilename(), file.getSize());
    }
}
