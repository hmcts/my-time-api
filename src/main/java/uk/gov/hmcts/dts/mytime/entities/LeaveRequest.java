package uk.gov.hmcts.dts.mytime.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.models.LeaveType;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class LeaveRequest {
    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "leave_request_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    @Column(insertable = false, updatable = false, nullable = false)
    private int id;

    @Column(nullable = false)
    private Integer employeeId;

    @Column(nullable = false)
    private Integer approverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveType type;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String requestComment;

    private String approverComment;

    public LeaveRequest(Integer employeeId, Integer approverId, LeaveType type, LeaveStatus status,
                        LocalDate startDate, LocalDate endDate, String requestComment, String approverComment) {
        this.employeeId = employeeId;
        this.approverId = approverId;
        this.type = type;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestComment = requestComment;
        this.approverComment = approverComment;
    }
}
