package io.github.mrergos.controller;

import io.github.mrergos.ecxeption.MemberExistsException;
import io.github.mrergos.ecxeption.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage("errors.400.title", null, "errors.400.title", locale));
        List<Map<String, String>> fieldErrors = ex.getFieldErrors().stream()
                .map(error -> Map.of(
                        "field",error.getField(),
                        "message",error.getDefaultMessage()
                ))
                .toList();
        problemDetail.setProperty("errors", fieldErrors);
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleMemberNotFoundException(MemberNotFoundException ex, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
        problemDetail.setProperty("member", ex.getUpdateMemberNksoPayload());
        problemDetail.setProperty("registryNum", ex.getRegistryNum());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(MemberExistsException.class)
    public ResponseEntity<ProblemDetail> handleMemberExistsException(MemberExistsException ex, Locale locale) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
        problemDetail.setProperty("member", ex.getCreateMemberNksoPayload());
        return ResponseEntity.badRequest()
                .body(problemDetail);
    }
}
