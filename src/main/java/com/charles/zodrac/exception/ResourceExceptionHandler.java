package com.charles.zodrac.exception;

import com.charles.zodrac.exception.dto.FieldErrorDTO;
import com.charles.zodrac.utils.LocaleUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class ResourceExceptionHandler {

    private final MessageSource ms;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException exception) {
        log.info("Handle access denied exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRuleException(BusinessRuleException exception) {
        log.info("Handle business rule exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        String message = ms.getMessage(exception.getMessage(), null, LocaleUtils.currentLocale());
        String status = ms.getMessage(exception.getStatus(), null, LocaleUtils.currentLocale());
        errorMap.put("message", message);
        errorMap.put("status", status);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FieldErrorDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        log.info("Handle http message not readable exception: {}", exception.getMessage());
        FieldErrorDTO error = new FieldErrorDTO(exception.getMessage(), exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        log.info("Handle illegal argument exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldErrorDTO>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.info("Handle method argument not valid exception: {}", exception.getMessage());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<FieldErrorDTO> fieldErrorDTOS = fieldErrors.stream().map(error -> new FieldErrorDTO(error.getField(), error.getDefaultMessage())).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorDTOS);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFound(UsernameNotFoundException exception) {
        log.info("Handle username not found exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
