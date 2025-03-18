package com.uplus.ureka.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CustomResponseDTO<T> {
    private final String status;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;
    private final String errorCode;
    private final String path;

    // Success
    public CustomResponseDTO(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.errorCode = null;
        this.path = null;
    }

    // Failure
    public CustomResponseDTO(String status, String message, String errorCode, String path) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.path = path;
    }
}