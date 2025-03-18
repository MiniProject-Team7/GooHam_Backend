package com.uplus.ureka.exception;

import com.uplus.ureka.dto.CustomResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponseDTO<Object>> handleCustomException(CustomException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(new CustomResponseDTO<>(
                        "error",
                        ex.getMessage(),
                        "400_BAD_REQUEST",
                        request.getRequestURI()
                ));
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
