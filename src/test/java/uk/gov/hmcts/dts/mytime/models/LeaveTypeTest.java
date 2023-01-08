package uk.gov.hmcts.dts.mytime.models;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.dts.mytime.exceptions.EnumConversionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LeaveTypeTest {
    @Test
    void testCreateEnumSuccess() {
        assertThat(LeaveType.create("HOLIDAY"))
            .as("Enum value does not match")
            .isEqualTo(LeaveType.HOLIDAY);
    }

    @Test
    void testCreateEnumWithIncorrectValue() {
        assertThatThrownBy(() -> LeaveType.create("HOLIDAYS"))
            .isInstanceOf(EnumConversionException.class)
            .hasMessage("Enum type 'HOLIDAYS' not expected");
    }

    @Test
    void testCreateEnumWithNullValue() {
        assertThatThrownBy(() -> LeaveType.create(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
