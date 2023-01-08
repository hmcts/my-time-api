package uk.gov.hmcts.dts.mytime.entities;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.models.LeaveType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LeaveRequestTest {
    private static final Integer EMPLOYEE_ID = 1;
    private static final Integer APPROVER_ID = 2;
    private static final LeaveType TYPE = LeaveType.HOLIDAY;
    private static final LeaveStatus STATUS = LeaveStatus.AWAITING;
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = START_DATE.plusDays(5);
    private static final LocalDate END_DATE2 = START_DATE.plusDays(10);
    private static final String REQUEST_COMMENT = "RequestComment";
    private static final String APPROVER_COMMENT = "ApproverComment";

    private static final LeaveRequest LEAVE_REQUEST = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS,
                                                                       START_DATE, END_DATE, REQUEST_COMMENT,
                                                                       APPROVER_COMMENT);

    @Test
    void testCreation() {
        assertThat(LEAVE_REQUEST)
            .as("Value does not match")
            .extracting(
                LeaveRequest::getEmployeeId,
                LeaveRequest::getApproverId,
                LeaveRequest::getType,
                LeaveRequest::getStatus,
                LeaveRequest::getStartDate,
                LeaveRequest::getEndDate,
                LeaveRequest::getRequestComment,
                LeaveRequest::getApproverComment)
            .containsExactly(
                EMPLOYEE_ID,
                APPROVER_ID,
                TYPE,
                STATUS,
                START_DATE,
                END_DATE,
                REQUEST_COMMENT,
                APPROVER_COMMENT
            );
    }

    @Test
    void testEqualsIfSameObject() {
        LeaveRequest anotherLeaveRequest = LEAVE_REQUEST;

        assertThat(LEAVE_REQUEST.equals(anotherLeaveRequest))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testEqualsIfOnlyIdFieldDifferent() {
        LeaveRequest anotherLeaveRequest = new LeaveRequest(200, EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS, START_DATE,
                                                            END_DATE, REQUEST_COMMENT, APPROVER_COMMENT);
        assertThat(LEAVE_REQUEST.equals(anotherLeaveRequest))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testNotEqualsIfNonIdFieldDifferent() {
        LeaveRequest anotherLeaveRequest = new LeaveRequest(100, EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS, START_DATE,
                                                            END_DATE2, REQUEST_COMMENT, APPROVER_COMMENT);
        assertThat(LEAVE_REQUEST.equals(anotherLeaveRequest))
            .as("Equals should not equal")
            .isFalse();
    }

    @Test
    void testHashCodeIfSameObject() {
        LeaveRequest anotherLeaveRequest = LEAVE_REQUEST;
        assertThat(LEAVE_REQUEST.hashCode())
            .as("Hashcode should equal")
            .isEqualTo(anotherLeaveRequest.hashCode());
    }

    @Test
    void testHashCodeIfOnlyIdFieldDifferent() {
        LeaveRequest anotherLeaveRequest = new LeaveRequest(200, EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS, START_DATE,
                                                            END_DATE, REQUEST_COMMENT, APPROVER_COMMENT);
        assertThat(LEAVE_REQUEST.hashCode())
            .as("Hashcode should equal")
            .isEqualTo(anotherLeaveRequest.hashCode());
    }

    @Test
    void testHashCodeIfNonIdFieldDifferent() {
        LeaveRequest anotherLeaveRequest = new LeaveRequest(100, EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS, START_DATE,
                                                            END_DATE2, REQUEST_COMMENT, APPROVER_COMMENT);
        assertThat(LEAVE_REQUEST.hashCode())
            .as("Hashcode should not equal")
            .isNotEqualTo(anotherLeaveRequest.hashCode());
    }
}
