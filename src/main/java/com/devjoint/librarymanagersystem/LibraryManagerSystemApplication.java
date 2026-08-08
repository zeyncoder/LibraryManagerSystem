package com.devjoint.librarymanagersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryManagerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerSystemApplication.class, args);
    }

}
