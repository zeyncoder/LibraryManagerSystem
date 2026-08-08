package com.devjoint.librarymanagersystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String saveFile(MultipartFile file) throws IOException;

    byte[] getFile(String fileName) throws IOException;

}