package uk.gov.hmcts.dts.mytime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.dts.mytime.exceptions.DuplicatedItemException;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Transactional
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        uk.gov.hmcts.dts.mytime.entities.LeaveRequest newLeaveRequestEntity =
            new uk.gov.hmcts.dts.mytime.entities.LeaveRequest(leaveRequest.getEmployeeId(),
                                                              leaveRequest.getApproverId(),
                                                              leaveRequest.getType(),
                                                              LeaveStatus.AWAITING,
                                                              leaveRequest.getStartDate(),
                                                              leaveRequest.getEndDate(),
                                                              leaveRequest.getRequestComment(),
                                                              leaveRequest.getApproverComment());

        if (leaveRequestRepository.findAllByEmployeeId(leaveRequest.getEmployeeId()).stream()
            .anyMatch(r -> r.equals(newLeaveRequestEntity))) {
            throw new DuplicatedItemException("Leave request already exists");
        }

        return new LeaveRequest(leaveRequestRepository.save(newLeaveRequestEntity));
    }
}
