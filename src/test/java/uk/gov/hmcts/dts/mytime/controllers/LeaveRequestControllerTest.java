package uk.gov.hmcts.dts.mytime.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.services.LeaveRequestService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
class LeaveRequestControllerTest {
    private static final Integer ID = 123;
    private static final Integer EMPLOYEE_ID = 456;
    private static final String ASSERTION_MESSAGE = "Leave request does not match";

    private static final LeaveRequest LEAVE_REQUEST = new LeaveRequest();
    private static final LeaveRequest RESULT = new LeaveRequest();

    @Mock
    private LeaveRequestService leaveRequestService;

    @InjectMocks
    private LeaveRequestController leaveRequestController;

    @BeforeAll
    static void setUp() {
        RESULT.setEmployeeId(EMPLOYEE_ID);
    }

    @Test
    void shouldCreateLeaveRequest() {
        when(leaveRequestService.createLeaveRequest(LEAVE_REQUEST)).thenReturn(RESULT);

        assertThat(leaveRequestController.createLeaveRequest(LEAVE_REQUEST))
            .as(ASSERTION_MESSAGE)
            .extracting(
                r -> r.getStatusCode(),
                r -> r.getBody())
            .containsExactly(
                HttpStatus.CREATED,
                RESULT
            );
    }

    @Test
    void shouldGetLeaveRequest() {
        when(leaveRequestService.getLeaveRequestById(ID)).thenReturn(RESULT);

        assertThat(leaveRequestController.getLeaveRequest(ID))
            .as(ASSERTION_MESSAGE)
            .extracting(
                r -> r.getStatusCode(),
                r -> r.getBody())
            .containsExactly(
                HttpStatus.OK,
                RESULT
            );
    }

    @Test
    void shouldDeleteLeaveRequest() {
        doNothing().when(leaveRequestService).deleteLeaveRequestById(ID);

        assertThat(leaveRequestController.deleteLeaveRequest(ID))
            .as(ASSERTION_MESSAGE)
            .extracting(
                r -> r.getStatusCode(),
                r -> r.getBody())
            .containsExactly(
                HttpStatus.OK,
                ID
            );
    }

    @Test
    void shouldGetAllEmployeeLeaveRequests() {
        when(leaveRequestService.getAllLeaveRequestsByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(RESULT));

        assertThat(leaveRequestController.getAllEmployeeLeaveRequests(EMPLOYEE_ID))
            .as(ASSERTION_MESSAGE)
            .extracting(
                r -> r.getStatusCode(),
                r -> r.getBody())
            .containsExactly(
                HttpStatus.OK,
                List.of(RESULT)
            );
    }
}
