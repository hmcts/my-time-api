//package uk.gov.hmcts.dts.mytime.repository;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import uk.gov.hmcts.dts.mytime.entities.LeaveRequest;
//import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
//import uk.gov.hmcts.dts.mytime.models.LeaveType;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Import(LeaveRequestRepository.class)
//@AutoConfigureDataJpa
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class LeaveRequestRepositoryTest {
//    private static final Integer EMPLOYEE_ID = 1;
//    private static final Integer NON_EXISTENT_EMPLOYEE_ID = 99;
//    private static final Integer APPROVER_ID = 2;
//    private static final LeaveType TYPE = LeaveType.HOLIDAY;
//    private static final LeaveStatus STATUS = LeaveStatus.AWAITING;
//    private static final LocalDate START_DATE = LocalDate.now();
//    private static final LocalDate END_DATE = START_DATE.plusDays(5);
//    private static final String REQUEST_COMMENT = "RequestComment";
//    private static final String APPROVER_COMMENT = "ApproverComment";
//
//    @Autowired
//    private LeaveRequestRepository leaveRequestRepository;
//
//    @BeforeAll
//    void setUp() {
//        LeaveRequest entity = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS, START_DATE, END_DATE,
//                                               REQUEST_COMMENT, APPROVER_COMMENT);
//        leaveRequestRepository.save(entity);
//    }
//
//    @Test
//    void shouldReturnLeaveRequestIfEmployeeIdExists() {
//        assertThat(leaveRequestRepository.findAllByEmployeeId(EMPLOYEE_ID))
//            .hasSize(1)
//            .first()
//            .extracting(r -> r.getEmployeeId())
//            .isEqualTo(EMPLOYEE_ID);
//    }
//
//    @Test
//    void shouldReturnEmptyIfEmployeeIdDoesNotExist() {
//        assertThat(leaveRequestRepository.findAllByEmployeeId(NON_EXISTENT_EMPLOYEE_ID))
//            .isEmpty();
//    }
//}
