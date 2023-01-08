package uk.gov.hmcts.dts.mytime.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomExceptionsTest {
    private static final String TEST_MESSAGE = "This is a test message";
    private static final String ERROR_MESSAGE = "Exception message does not match";

    @Test
    void testEnumConversionException() {
        EnumConversionException exception = new EnumConversionException(TEST_MESSAGE);
        assertThat(exception.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo(TEST_MESSAGE);
    }

    @Test
    void testDuplicateItemException() {
        DuplicatedItemException exception = new DuplicatedItemException(TEST_MESSAGE);
        assertThat(exception.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo(TEST_MESSAGE);
    }

    @Test
    void testNotFoundException() {
        NotFoundException exception = new NotFoundException(TEST_MESSAGE);
        assertThat(exception.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo(TEST_MESSAGE);
    }
}
