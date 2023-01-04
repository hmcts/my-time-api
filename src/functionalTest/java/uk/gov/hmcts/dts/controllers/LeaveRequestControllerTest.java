package uk.gov.hmcts.dts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;
import uk.gov.hmcts.dts.mytime.exceptions.ExceptionResponse;
import uk.gov.hmcts.dts.mytime.models.LeaveRequest;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.models.LeaveType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(SpringExtension.class)
class LeaveRequestControllerTest extends FunctionalTestBase  {
    private static final String PATH = "/leave-request";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Integer EMPLOYEE_ID = 1;
    private static final Integer EMPLOYEE_ID2 = 2;
    private static final Integer APPROVER_ID = 9;
    private static final LeaveType TYPE = LeaveType.HOLIDAY;
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = START_DATE.plusDays(5);

    @BeforeAll
    static void setup() {
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @Test
    void shouldCreateNewLeaveRequest() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, TYPE, null,
                                 START_DATE, END_DATE, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        LeaveRequest leaveRequest = response.getBody().as(LeaveRequest.class);
        assertThat(leaveRequest.getEmployeeId()).isEqualTo(EMPLOYEE_ID);
        assertThat(leaveRequest.getStatus()).isEqualTo(LeaveStatus.AWAITING);
    }

    @Test
    void shouldReturnConflictIfLeaveRequestAlreadyExists() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID2, APPROVER_ID, TYPE, null,
                                              START_DATE, END_DATE, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode()).isEqualTo(CONFLICT.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo("Leave request already exists");
    }

    @Test
    void shouldReturnBadRequestIfFieldInvalid() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, null, TYPE, null,
                                              START_DATE, END_DATE, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage()).isEqualTo("Approver ID is required");
    }
}
