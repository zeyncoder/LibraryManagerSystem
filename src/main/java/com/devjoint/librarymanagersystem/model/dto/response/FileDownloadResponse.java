package com.devjoint.librarymanagersystem.model.dto.response;
import org.springframework.http.MediaType;
public record FileDownloadResponse(
    byte[] data,
    MediaType contentType
){
}
