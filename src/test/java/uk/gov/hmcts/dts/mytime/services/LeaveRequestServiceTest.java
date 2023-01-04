package uk.gov.hmcts.dts.mytime.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.repository.LeaveRequestRepository;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceTest {
    private static final Integer EMPLOYEE_ID = 123;
    private static final LeaveRequest INPUT_REQUEST = new LeaveRequest();
    private static final uk.gov.hmcts.dts.mytime.entities.LeaveRequest ENTITY =
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
    }

    @Test
    void shouldCreateLeaveRequest() {
        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(emptyList());
        when(leaveRequestRepository.save(ENTITY)).thenReturn(ENTITY);

        LeaveRequest result = leaveRequestService.createLeaveRequest(INPUT_REQUEST);
        assertThat(result.getEmployeeId()).isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldThrowExceptionIfSameLeaveRequestExists() {
        uk.gov.hmcts.dts.mytime.entities.LeaveRequest entity = new uk.gov.hmcts.dts.mytime.entities.LeaveRequest();
        entity.setEmployeeId(EMPLOYEE_ID);
        entity.setStatus(LeaveStatus.AWAITING);

        when(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(entity));

        assertThatThrownBy(() -> leaveRequestService.createLeaveRequest(INPUT_REQUEST))
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
            .isEqualTo(EMPLOYEE_ID);
        assertThat(result.getStatus())
            .isEqualTo(LeaveStatus.AWAITING);
    }
}
