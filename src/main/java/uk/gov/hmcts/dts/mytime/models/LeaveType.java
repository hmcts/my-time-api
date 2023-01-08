package uk.gov.hmcts.dts.mytime.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import uk.gov.hmcts.dts.mytime.exceptions.EnumConversionException;

public enum LeaveType {
    FLEXI,
    HOLIDAY;

    @JsonCreator
    public static LeaveType create(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (LeaveType type : values()) {
            if (value.equals(type.toString())) {
                return type;
            }
        }
        throw new EnumConversionException(String.format("Enum type '%s' not expected", value));
    }
}
