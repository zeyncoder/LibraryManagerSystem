package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.model.dto.response.FileDownloadResponse;
import com.devjoint.librarymanagersystem.service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDirectory =
            Paths.get("uploads/covers")
                    .toAbsolutePath()
                    .normalize();

    @Override
    public String saveFile(MultipartFile file) throws IOException {

        Files.createDirectories(uploadDirectory);

        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(
                    originalFileName.lastIndexOf(".")
            );
        }

        String fileName = UUID.randomUUID() + extension;

        Path filePath = getSafePath(fileName);

        Files.write(filePath, file.getBytes());

        return fileName;
    }

    @Override
    public FileDownloadResponse getFile(String fileName) throws IOException {

        Path filePath = getSafePath(fileName);

        byte[] data = Files.readAllBytes(filePath);

        String contentType = Files.probeContentType(filePath);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return new FileDownloadResponse(
                data,
                MediaType.parseMediaType(contentType)
        );
    }

    @Override
    public void deleteFile(String fileName) throws IOException {

        if (fileName == null || fileName.isBlank()) {
            return;
        }

        Path filePath = getSafePath(fileName);

        Files.deleteIfExists(filePath);
    }

    private Path getSafePath(String fileName) {

        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException(
                    "File name cannot be empty"
            );
        }

        Path filePath = uploadDirectory
                .resolve(fileName)
                .normalize();

        if (!filePath.startsWith(uploadDirectory)) {
            throw new SecurityException("Invalid file path");
        }

        return filePath;
    }
}