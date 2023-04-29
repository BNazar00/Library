package com.bn.library.service.impl;

import com.bn.library.dto.upload.ImageUploadResponse;
import com.bn.library.exception.FileDeleteException;
import com.bn.library.exception.FileUploadException;
import com.bn.library.exception.IncorrectInputException;
import com.bn.library.service.FileService;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private static final String FILE_UPLOAD_EXCEPTION = "Could not save image file: %s";
    private static final String DIRECTORY_CREATE_EXCEPTION = "Could not create directory with name: %s";
    private static final String IMAGE_SIZE_EXCEPTION = "Max image size should be %d bytes, your image size is %d bytes";
    private static final String IMAGE_RESOLUTION_EXCEPTION = "Image %s should be more than %d, your image %s is %d";
    private static final String UPLOAD_LOCATION = "/upload";
    private static final Long IMAGE_SIZE_MB = 5L;
    private static final Long IMAGE_SIZE_B = IMAGE_SIZE_MB * 1024 * 1024;
    private static final Long MIN_IMAGE_WIDTH = 200L;
    private static final Long MIN_IMAGE_HEIGHT = 200L;
    private final String uploadDirectory;

    public FileServiceImpl(@Value("${application.upload.path}") String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    @Override
    public ImageUploadResponse uploadImage(MultipartFile multipartFile) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss_");
        String fileName = now.format(formatter) + multipartFile.getOriginalFilename();
        return uploadImage(fileName, multipartFile);
    }

    @Override
    public ImageUploadResponse uploadImage(String fileName, MultipartFile multipartFile) {
        Path uploadPath = Paths.get(uploadDirectory);

        if (multipartFile.getSize() > IMAGE_SIZE_B) {
            throw new IncorrectInputException(
                    String.format(IMAGE_SIZE_EXCEPTION, IMAGE_SIZE_B, multipartFile.getSize()));
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width < MIN_IMAGE_WIDTH) {
                throw new IncorrectInputException(
                        String.format(IMAGE_RESOLUTION_EXCEPTION, "width", MIN_IMAGE_WIDTH, "width", width));
            }
            if (height < MIN_IMAGE_HEIGHT) {
                throw new IncorrectInputException(
                        String.format(IMAGE_RESOLUTION_EXCEPTION, "height", MIN_IMAGE_HEIGHT, "height", height));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new FileUploadException(String.format(DIRECTORY_CREATE_EXCEPTION, fileName));
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new FileUploadException(String.format(FILE_UPLOAD_EXCEPTION, fileName));
        }

        String actualPath = String.format("/%s/%s", uploadDirectory, fileName);
        return new ImageUploadResponse(fileName, "done",
                actualPath.substring(actualPath.indexOf(UPLOAD_LOCATION)));
    }

    @Override
    public void deleteImage(String imageName) {
        try {
            String filePath = String.format("%s/%s", uploadDirectory, imageName);
            Path uploadPath = Paths.get(filePath);
            Files.delete(uploadPath);
        } catch (IOException e) {
            log.error("Failed to delete an image ", e);
            throw new FileDeleteException();
        }
    }
}
