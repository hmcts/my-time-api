package uk.gov.hmcts.dts.mytime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.repository.LeaveRequestRepository;

import java.util.List;

@Service
public class LeaveRequestService {
    private static final String LEAVE_REQUEST_MESSAGE = "Leave Request with ID '%s' does not exist";
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Transactional
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        uk.gov.hmcts.dts.mytime.entities.LeaveRequest newLeaveRequestEntity =
            new uk.gov.hmcts.dts.mytime.entities.LeaveRequest(
                leaveRequest.getEmployeeId(),
                leaveRequest.getApproverId(),
                leaveRequest.getType(),
                LeaveStatus.AWAITING,
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getRequestComment(),
                leaveRequest.getApproverComment()
            );

        if (leaveRequestRepository.findAllByEmployeeId(leaveRequest.getEmployeeId()).stream()
            .anyMatch(r -> r.equals(newLeaveRequestEntity))) {
            throw new DuplicatedItemException("Leave request already exists");
        }

        return new LeaveRequest(leaveRequestRepository.save(newLeaveRequestEntity));
    }

    @Transactional
    public LeaveRequest updateLeaveRequest(LeaveRequest leaveRequest) {
        // Check if Leave Request being updated actually exists
        if (leaveRequestRepository.findById(leaveRequest.getId()).stream()
            .noneMatch(r -> r.getId().equals(leaveRequest.getId()))) {
            throw new NotFoundException(String.format(LEAVE_REQUEST_MESSAGE, leaveRequest.getId()));
        }

        uk.gov.hmcts.dts.mytime.entities.LeaveRequest newLeaveRequestEntity =
            new uk.gov.hmcts.dts.mytime.entities.LeaveRequest(
                leaveRequest.getId(),
                leaveRequest.getEmployeeId(),
                leaveRequest.getApproverId(),
                leaveRequest.getType(),
                leaveRequest.getStatus(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getRequestComment(),
                leaveRequest.getApproverComment()
            );

        return new LeaveRequest(leaveRequestRepository.save(newLeaveRequestEntity));
    }

    public LeaveRequest getLeaveRequestById(Integer id) {
        return leaveRequestRepository.findById(id)
            .map(LeaveRequest::new)
            .orElseThrow(() -> new NotFoundException(String.format(LEAVE_REQUEST_MESSAGE, id)));
    }

    public void deleteLeaveRequestById(Integer id) {
        leaveRequestRepository.findById(id)
            .ifPresentOrElse(
                o -> leaveRequestRepository.deleteById(id),
                () -> {
                    throw new NotFoundException(String.format(LEAVE_REQUEST_MESSAGE, id));
                });
    }

    public List<LeaveRequest> getAllLeaveRequestsByEmployeeId(Integer employeeId) {
        // TODO: Once we merge with the 'user' tickets, need to check the employee ID exists and if not throws
        //  a not found exception
        return leaveRequestRepository.findAllByEmployeeId(employeeId)
            .stream()
            .map(LeaveRequest::new)
            .toList();
    }
}
