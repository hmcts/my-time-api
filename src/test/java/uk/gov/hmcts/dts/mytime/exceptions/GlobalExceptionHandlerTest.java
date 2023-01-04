package uk.gov.hmcts.dts.mytime.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
class GlobalExceptionHandlerTest {
    private static final String TEST_MESSAGE = "This is a test message";
    private static final String ANOTHER_TEST_MESSAGE = "This is another test message";
    private static final String ERROR_MESSAGE = "Response does not match";

    @Mock
    FieldError fieldError;

    @Mock
    FieldError anotherFieldError;

    @Mock
    private BindingResult bindingResult;

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleMethodArgumentNotValidException() {
        when(fieldError.getDefaultMessage()).thenReturn(TEST_MESSAGE);
        when(anotherFieldError.getDefaultMessage()).thenReturn(ANOTHER_TEST_MESSAGE);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError, anotherFieldError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        String expectedExceptionMessage = List.of(TEST_MESSAGE, ANOTHER_TEST_MESSAGE)
            .stream()
            .collect(Collectors.joining(", "));

        assertThat(globalExceptionHandler.handle(exception))
            .as(ERROR_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                e -> e.getBody().getMessage())
            .containsExactly(
                HttpStatus.BAD_REQUEST,
                expectedExceptionMessage);
    }

    @Test
    void testHandleEnumConversionException() {
        EnumConversionException exception = new EnumConversionException(TEST_MESSAGE);

        assertThat(globalExceptionHandler.handle(exception))
            .as(ERROR_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                e -> e.getBody().getMessage())
            .containsExactly(
                HttpStatus.BAD_REQUEST,
                TEST_MESSAGE);
    }

    @Test
    void testHandleDuplicateItemException() {
        DuplicatedItemException exception = new DuplicatedItemException(TEST_MESSAGE);

        assertThat(globalExceptionHandler.handle(exception))
            .as(ERROR_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                e -> e.getBody().getMessage())
            .containsExactly(
                HttpStatus.CONFLICT,
                TEST_MESSAGE);
    }

    @Test
    void testHandleNotFoundException() {
        NotFoundException exception = new NotFoundException(TEST_MESSAGE);

        assertThat(globalExceptionHandler.handle(exception))
            .as(ERROR_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                e -> e.getBody().getMessage())
            .containsExactly(
                HttpStatus.NOT_FOUND,
                TEST_MESSAGE);
    }
}
