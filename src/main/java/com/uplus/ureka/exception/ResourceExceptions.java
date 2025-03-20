package com.uplus.ureka.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceExceptions extends RuntimeException {
    private final HttpStatus status;

    public ResourceExceptions(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND; // 기본 상태 코드
    }

    public ResourceExceptions(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}