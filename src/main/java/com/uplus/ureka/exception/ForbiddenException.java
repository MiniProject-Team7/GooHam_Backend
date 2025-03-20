package com.uplus.ureka.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends RuntimeException {
    private final HttpStatus status;

    public ForbiddenException(String message) {
        super(message);
        this.status = HttpStatus.FORBIDDEN; // 기본 상태 코드
    }

    public ForbiddenException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}