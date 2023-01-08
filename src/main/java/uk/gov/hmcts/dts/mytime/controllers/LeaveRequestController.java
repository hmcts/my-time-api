package uk.gov.hmcts.dts.mytime.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.services.LeaveRequestService;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/leave-request")
@Validated
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LeaveRequest> createLeaveRequest(@Valid @RequestBody LeaveRequest leaveRequest) {
        return created(URI.create(StringUtils.EMPTY))
            .body(leaveRequestService.createLeaveRequest(leaveRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequest(
        @Valid @Min(value = 1, message = "Leave request ID must be greater than zero") @PathVariable Integer id
    ) {
        return ok(leaveRequestService.getLeaveRequestById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteLeaveRequest(
        @Valid @Min(value = 1, message = "Leave request ID must be greater than zero") @PathVariable Integer id
    ) {
        leaveRequestService.deleteLeaveRequestById(id);
        return ok(id);
    }

    @GetMapping("/{employeeId}/all")
    public ResponseEntity<List<LeaveRequest>> getAllEmployeeLeaveRequests(
        @Valid @Min(value = 1, message = "Employee ID must be greater than zero") @PathVariable Integer employeeId
    ) {
        return ok(leaveRequestService.getAllLeaveRequestsByEmployeeId(employeeId));
    }
}
