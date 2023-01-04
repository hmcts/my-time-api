package uk.gov.hmcts.dts.mytime.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.services.LeaveRequestService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveRequestControllerTest {
    private final LeaveRequest leaveRequest = new LeaveRequest();
    private final LeaveRequest result = new LeaveRequest();

    @Mock
    private LeaveRequestService leaveRequestService;

    @InjectMocks
    private LeaveRequestController leaveRequestController;

    @Test
    @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
    void shouldCreateLeaveRequest() {
        when(leaveRequestService.createLeaveRequest(leaveRequest)).thenReturn(result);

        assertThat(leaveRequestController.createLeaveRequest(leaveRequest))
            .as("Leave request does not match")
            .extracting(
                r -> r.getStatusCode(),
                r -> r.getBody())
            .containsExactly(
                HttpStatus.CREATED,
                result);
    }
}
