package uk.gov.hmcts.dts.mytime.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private LocalDateTime timestamp;
}
