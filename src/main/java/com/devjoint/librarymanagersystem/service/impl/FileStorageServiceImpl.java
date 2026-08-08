package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.service.FileStorageService;
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
            Paths.get("uploads/covers");

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

        Path filePath = uploadDirectory.resolve(fileName);

        Files.write(filePath, file.getBytes());

        return fileName;
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {

        Path filePath = uploadDirectory.resolve(fileName);

        return Files.readAllBytes(filePath);
    }
}