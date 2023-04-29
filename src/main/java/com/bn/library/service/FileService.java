package com.bn.library.service;

import com.bn.library.dto.upload.ImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ImageUploadResponse uploadImage(MultipartFile multipartFile);

    ImageUploadResponse uploadImage(String fileName, MultipartFile multipartFile);

    void deleteImage(String imageName);
}
