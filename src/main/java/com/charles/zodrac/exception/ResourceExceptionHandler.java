package com.charles.zodrac.exception;

import com.charles.zodrac.utils.LocaleUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class ResourceExceptionHandler {

    private final MessageSource ms;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> errorCustom(CustomException exception) {
        log.info("Handle custom exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        String message = ms.getMessage(exception.getMessage(), null, LocaleUtils.currentLocale());
        errorMap.put("message", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException exception) {
        log.info("Handle access denied exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        log.info("Handle illegal argument exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFound(UsernameNotFoundException exception) {
        log.info("Handle username not found exception: {}", exception.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}
