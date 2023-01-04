package uk.gov.hmcts.dts.mytime.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import uk.gov.hmcts.dts.mytime.exceptions.EnumConversionException;

public enum LeaveStatus {
    AWAITING,
    APPROVED,
    DENIED;

    @JsonCreator
    public static LeaveStatus create(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (LeaveStatus status : values()) {
            if (value.equals(status.toString())) {
                return status;
            }
        }
        throw new EnumConversionException(String.format("Enum status '%s' not expected", value));
    }
}
