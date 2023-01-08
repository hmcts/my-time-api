package uk.gov.hmcts.dts.mytime.models;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.dts.mytime.exceptions.EnumConversionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LeaveStatusTest {
    @Test
    void testCreateEnumSuccess() {
        assertThat(LeaveStatus.create("APPROVED"))
            .as("Enum value does not match")
            .isEqualTo(LeaveStatus.APPROVED);
    }

    @Test
    void testCreateEnumWithIncorrectValue() {
        assertThatThrownBy(() -> LeaveStatus.create("APPROVING"))
            .isInstanceOf(EnumConversionException.class)
            .hasMessage("Enum status 'APPROVING' not expected");
    }

    @Test
    void testCreateEnumWithNullValue() {
        assertThatThrownBy(() -> LeaveStatus.create(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
