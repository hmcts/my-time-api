package uk.gov.hmcts.dts.mytime.exceptions;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionResponseTest {
    private static final String TEST_MESSAGE = "This is a test message";

    @Test
    void testCreationOfExceptionResponse() {
        LocalDateTime timestamp = LocalDateTime.now();
        ExceptionResponse response = new ExceptionResponse(TEST_MESSAGE, timestamp);

        assertThat(response.getMessage())
            .as("Message does not match")
            .isEqualTo(TEST_MESSAGE);

        assertThat(response.getTimestamp())
            .as("Timestamp does not match")
            .isEqualTo(timestamp);
    }
}
