package com.devjoint.librarymanagersystem.service;

import com.devjoint.librarymanagersystem.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskService {

    private final BookRepository bookRepository;
    private final FileStorageService fileStorageService;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanupUnusedFiles() {

        log.info("Scheduled cleanup task started");

        Path uploadDirectory = Paths.get("uploads/covers");

        if (!Files.exists(uploadDirectory)) {
            log.info("Upload directory does not exist");
            return;
        }

        Set<String> usedFiles = bookRepository.findAllCoverImages()
                .stream()
                .collect(Collectors.toSet());

        try {
            Files.list(uploadDirectory)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        String fileName = file.getFileName().toString();

                        if (!usedFiles.contains(fileName)) {
                            try {
                                fileStorageService.deleteFile(fileName);
                                log.info("Unused file deleted: {}", fileName);
                            } catch (IOException e) {
                                log.error("Failed to delete file: {}", fileName, e);
                            }
                        }
                    });

        } catch (IOException e) {
            log.error("Failed to scan upload directory", e);
        }

        log.info("Scheduled cleanup task completed");
    }
}