package com.bn.library.controller;

import com.bn.library.dto.upload.ImageUploadResponse;
import com.bn.library.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/image")
    public ImageUploadResponse uploadImage(@RequestParam("image") MultipartFile image) {
        log.info("Image upload request");
        return fileService.uploadImage(image);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/image")
    public void deleteImage(@RequestParam("imageName") String imageName) {
        log.info("Image delete request");
        fileService.deleteImage(imageName);
    }
}
