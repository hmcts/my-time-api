package uk.gov.hmcts.dts.mytime.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.repository.LeaveRequestRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {
    private static final Integer ID = 123;
    private static final Integer ID2 = 124;
    private static final Integer EMPLOYEE_ID = 456;
    private static final String LEAVE_REQUEST_MESSAGE = "Leave request does not match";
    private static final String STATUS_MESSAGE = "Status does not match";
    private static final String EXCEPTION_MESSAGE = "Exception does not match";

    private static final LeaveRequest INPUT_REQUEST = new LeaveRequest();
    private static final uk.gov.hmcts.dts.mytime.entities.LeaveRequest ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.LeaveRequest();
    private static final uk.gov.hmcts.dts.mytime.entities.LeaveRequest ENTITY2 =
        new uk.gov.hmcts.dts.mytime.entities.LeaveRequest();

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @InjectMocks
    private LeaveRequestService leaveRequestService;

    @BeforeAll
    static void setUp() {
        INPUT_REQUEST.setEmployeeId(EMPLOYEE_ID);

        ENTITY.setEmployeeId(EMPLOYEE_ID);
        ENTITY.setStatus(LeaveStatus.AWAITING);
        ENTITY.setId(ID);

        ENTITY2.setEmployeeId(EMPLOYEE_ID);
        ENTITY2.setStatus(LeaveStatus.APPROVED);
        ENTITY2.setId(ID2);
    }

    @Test
    void shouldCreateLeaveRequest() {
        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(emptyList());
        when(leaveRequestRepository.save(ENTITY)).thenReturn(ENTITY);

        LeaveRequest result = leaveRequestService.createLeaveRequest(INPUT_REQUEST);
        assertThat(result.getEmployeeId())
            .as(LEAVE_REQUEST_MESSAGE)
            .isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldThrowExceptionWhenCreatingExistingLeaveRequest() {
        uk.gov.hmcts.dts.mytime.entities.LeaveRequest entity = new uk.gov.hmcts.dts.mytime.entities.LeaveRequest();
        entity.setEmployeeId(EMPLOYEE_ID);
        entity.setStatus(LeaveStatus.AWAITING);

        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(entity));

        assertThatThrownBy(() -> leaveRequestService.createLeaveRequest(INPUT_REQUEST))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(DuplicatedItemException.class)
            .hasMessage("Leave request already exists");

        verifyNoMoreInteractions(leaveRequestRepository);
    }

    @Test
    void shouldCreateLeaveRequestIfHaveOtherLeaveRequests() {
        uk.gov.hmcts.dts.mytime.entities.LeaveRequest entityToCreate =
            new uk.gov.hmcts.dts.mytime.entities.LeaveRequest();
        entityToCreate.setEmployeeId(EMPLOYEE_ID);
        entityToCreate.setStatus(LeaveStatus.AWAITING);

        uk.gov.hmcts.dts.mytime.entities.LeaveRequest existingEntity =
            new uk.gov.hmcts.dts.mytime.entities.LeaveRequest();
        existingEntity.setEmployeeId(EMPLOYEE_ID);
        existingEntity.setStatus(LeaveStatus.APPROVED);

        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(existingEntity));
        when(leaveRequestRepository.save(entityToCreate)).thenReturn(entityToCreate);

        LeaveRequest result = leaveRequestService.createLeaveRequest(INPUT_REQUEST);

        assertThat(result.getEmployeeId())
            .as(LEAVE_REQUEST_MESSAGE)
            .isEqualTo(EMPLOYEE_ID);

        assertThat(result.getStatus())
            .as(STATUS_MESSAGE)
            .isEqualTo(LeaveStatus.AWAITING);
    }

    @Test
    void shouldGetLeaveRequest() {
        when(leaveRequestRepository.findById(ID)).thenReturn(Optional.of(ENTITY));

        assertThat(leaveRequestService.getLeaveRequestById(ID))
            .as(LEAVE_REQUEST_MESSAGE)
            .isNotNull()
            .extracting(LeaveRequest::getEmployeeId)
            .isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldThrowExceptionIfGetLeaveRequestNotFound() {
        when(leaveRequestRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> leaveRequestService.getLeaveRequestById(ID))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Leave request with ID '123' does not exist");
    }

    @Test
    void shouldDeleteLeaveRequest() {
        when(leaveRequestRepository.findById(ID)).thenReturn(Optional.of(ENTITY));

        assertThatCode(() -> leaveRequestService.deleteLeaveRequestById(ID))
            .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionIfDeleteLeaveRequestNotFound() {
        when(leaveRequestRepository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> leaveRequestService.deleteLeaveRequestById(ID))
            .as(EXCEPTION_MESSAGE)
            .isInstanceOf(NotFoundException.class)
            .hasMessage("Leave request with ID '123' does not exist");
    }

    @Test
    void shouldGetAllLeaveRequestsForAnEmployee() {
        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(ENTITY, ENTITY2));

        assertThat(leaveRequestService.getAllLeaveRequestsByEmployeeId(EMPLOYEE_ID))
            .as(LEAVE_REQUEST_MESSAGE)
            .hasSize(2)
            .allSatisfy(r -> r.getEmployeeId().equals(EMPLOYEE_ID));
    }

    @Test
    void shouldGetNoLeaveRequestForAnEmployee() {
        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(emptyList());

        assertThat(leaveRequestService.getAllLeaveRequestsByEmployeeId(EMPLOYEE_ID))
            .as(LEAVE_REQUEST_MESSAGE)
            .isEmpty();
    }
}
