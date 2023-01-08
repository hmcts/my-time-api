package uk.gov.hmcts.dts.mytime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {
    @NotNull(message = "Employee ID is required")
    Integer employeeId;

    @NotNull(message = "Approver ID is required")
    Integer approverId;

    @NotNull(message = "Leave type is required")
    LeaveType type;

    LeaveStatus status;

    @NotNull(message = "start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDate endDate;

    String requestComment;

    String approverComment;

    public LeaveRequest(uk.gov.hmcts.dts.mytime.entities.LeaveRequest entity) {
        this.employeeId = entity.getEmployeeId();
        this.approverId = entity.getApproverId();
        this.type = entity.getType();
        this.status = entity.getStatus();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.requestComment = entity.getRequestComment();
        this.approverComment = entity.getApproverComment();
    }
}
