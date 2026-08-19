package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.model.dto.response.FileDownloadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String saveFile(MultipartFile file) throws IOException;

    FileDownloadResponse getFile(String fileName) throws IOException;
    void deleteFile(String fileName) throws IOException;

}