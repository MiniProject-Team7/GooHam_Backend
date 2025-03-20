package com.uplus.ureka.exception;

import com.uplus.ureka.dto.CustomResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Request Body Error
    @ExceptionHandler(CustomExceptions.class)
    public ResponseEntity<CustomResponseDTO<Object>> handleCustomException(CustomExceptions ex, HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(new CustomResponseDTO<>(
                        "error",
                        ex.getMessage(),
                        "400_BAD_REQUEST",
                        request.getRequestURI()
                ));
    }

    // Authorization error
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<CustomResponseDTO<Object>> handleAuthException(AuthException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new CustomResponseDTO<>(
                        "error",
                        ex.getMessage(),
                        "401_UNAUTHORIZED",
                        request.getRequestURI()
                ));
    }

    // Forbidden Error
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomResponseDTO<Object>> handleAuthException(ForbiddenException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new CustomResponseDTO<>(
                        "error",
                        ex.getMessage(),
                        "403_FORBIDDEN",
                        request.getRequestURI()
                ));
    }

    //Resource Not Found Error
    @ExceptionHandler(ResourceExceptions.class)
    public ResponseEntity<CustomResponseDTO<Object>> handleAuthException(ResourceExceptions ex, HttpServletRequest request) {
        return ResponseEntity.notFound()
                .build();
    }



    // General
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponseDTO<Object>> handleGeneralException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.internalServerError()
                .body(new CustomResponseDTO<>(
                        "error",
                        "서버 내부 오류 발생",
                        "500_INTERNAL_SERVER_ERROR",
                        request.getRequestURI()
                ));
    }
}
