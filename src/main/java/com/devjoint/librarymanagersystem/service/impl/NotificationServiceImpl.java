package com.devjoint.librarymanagersystem.service.impl;

import com.devjoint.librarymanagersystem.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Async("taskExecutor")
    @Override
    public void sendNotification(String message) {
        log.info("Async notification started: {}", message);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Async notification completed: {}", message);
    }
}