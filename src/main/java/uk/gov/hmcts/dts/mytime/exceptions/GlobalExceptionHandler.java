package uk.gov.hmcts.dts.mytime.exceptions;

import groovy.util.logging.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handle(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateExceptionResponse(error));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handle(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(generateExceptionResponse(ex));
    }

    @ExceptionHandler(EnumConversionException.class)
    public ResponseEntity<ExceptionResponse> handle(EnumConversionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(generateExceptionResponse(ex));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(generateExceptionResponse(ex));
    }

    @ExceptionHandler(DuplicatedItemException.class)
    public ResponseEntity<ExceptionResponse> handle(DuplicatedItemException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(generateExceptionResponse(ex));
    }

    private ExceptionResponse generateExceptionResponse(Exception ex) {
        return generateExceptionResponse(ex.getMessage());
    }

    private ExceptionResponse generateExceptionResponse(String message) {
        return new ExceptionResponse(message, LocalDateTime.now());
    }
}
