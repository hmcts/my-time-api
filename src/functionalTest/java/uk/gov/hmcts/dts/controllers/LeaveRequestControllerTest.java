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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SuppressWarnings("PMD.TooManyMethods")
class LeaveRequestControllerTest extends FunctionalTestBase  {
    private static final String PATH = "/leave-request";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Integer ID = 1;
    private static final Integer EMPLOYEE_ID = 2;
    private static final Integer APPROVER_ID = 9;
    private static final Integer NON_EXISTENT_ID = 99;
    private static final LeaveType TYPE = LeaveType.HOLIDAY;
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = START_DATE.plusDays(5);
    private static final LocalDate END_DATE_PLUS_1 = END_DATE.plusDays(1);
    private static final LocalDate END_DATE_PLUS_2 = END_DATE.plusDays(2);
    private static final LocalDate END_DATE_PLUS_3 = END_DATE.plusDays(3);
    private static final LocalDate END_DATE_PLUS_4 = END_DATE.plusDays(4);

    private static final String LEAVE_REQUEST_MESSAGE = "Leave request does not match";
    private static final String STATUS_MESSAGE = "Status does not match";
    private static final String ERROR_MESSAGE = "Error message does not match";

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
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(CREATED.value());

        LeaveRequest leaveRequest = response.getBody().as(LeaveRequest.class);
        assertThat(leaveRequest.getEmployeeId())
            .as(LEAVE_REQUEST_MESSAGE)
            .isEqualTo(EMPLOYEE_ID);
        assertThat(leaveRequest.getStatus())
            .as(STATUS_MESSAGE)
            .isEqualTo(LeaveStatus.AWAITING);
    }

    @Test
    void shouldReturnConflictIfCreatingExistingLeaveRequest() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, TYPE, null,
                                              START_DATE, END_DATE_PLUS_1, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(CREATED.value());

        response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(CONFLICT.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo("Leave request already exists");
    }

    @Test
    void shouldReturnBadRequestIfCreatingLeaveRequestWithInvalidField() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, null, TYPE, null,
                                              START_DATE, END_DATE, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(BAD_REQUEST.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo("Approver ID is required");
    }

    @Test
    void shouldGetExistingLeaveRequest() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, LeaveType.FLEXI, LeaveStatus.APPROVED,
                                              START_DATE, END_DATE_PLUS_2, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(CREATED.value());

        String path = PATH + "/" + ID;
        response = doGetRequest(path);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(OK.value());

        LeaveRequest leaveRequest = response.getBody().as(LeaveRequest.class);
        assertThat(leaveRequest.getEmployeeId())
            .as(LEAVE_REQUEST_MESSAGE)
            .isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldReturnNotFoundIfGettingNonExistentLeaveRequest() {
        var response = doGetRequest(PATH + "/" + NON_EXISTENT_ID);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(NOT_FOUND.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo("Leave request with ID '99' does not exist");
    }

    @Test
    void shouldGetBadRequestIfGettingLeaveRequestWithIdZero() {
        String path = PATH + "/0";
        var response = doGetRequest(path);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(BAD_REQUEST.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .contains("Leave request ID must be greater than zero");
    }

    @Test
    void shouldDeleteExistingLeaveRequest() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, LeaveType.FLEXI, LeaveStatus.APPROVED,
                                              START_DATE, END_DATE_PLUS_3, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode()).isEqualTo(CREATED.value());

        String path = PATH + "/" + ID;
        response = doDeleteRequest(path);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(OK.value());

        assertThat(response.getBody().as(Integer.class))
            .as(LEAVE_REQUEST_MESSAGE)
            .isEqualTo(1);
    }

    @Test
    void shouldReturnNotFoundIfDeletingNonExistentLeaveRequest() {
        var response = doGetRequest(PATH + "/" + NON_EXISTENT_ID);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(NOT_FOUND.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .isEqualTo("Leave request with ID '99' does not exist");
    }

    @Test
    void shouldGetBadRequestIfDeletingLeaveRequestWithIdZero() {
        String path = PATH + "/0";
        var response = doDeleteRequest(path);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(BAD_REQUEST.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .contains("Leave request ID must be greater than zero");
    }

    @Test
    void shouldGetAllLeaveRequestForTheEmployee() throws JsonProcessingException {
        LeaveRequest input = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, LeaveType.FLEXI, LeaveStatus.APPROVED,
                                              START_DATE, END_DATE_PLUS_4, null, null);
        String jsonInput = OBJECT_MAPPER.writeValueAsString(input);

        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(CREATED.value());

        String path = PATH + "/" + EMPLOYEE_ID + "/all";
        response = doGetRequest(path);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(OK.value());

        final List<LeaveRequest> leaveRequests = List.of(response.getBody().as(LeaveRequest[].class));
        assertThat(leaveRequests)
            .as(LEAVE_REQUEST_MESSAGE)
            .isNotEmpty();
    }

    @Test
    void shouldGetBadRequestIfGettingAllLeaveRequestsWithEmployeeIdZero() {
        String path = PATH + "/" + 0 + "/all";
        var response = doGetRequest(path);
        assertThat(response.statusCode())
            .as(STATUS_MESSAGE)
            .isEqualTo(BAD_REQUEST.value());

        ExceptionResponse exceptionResponse = response.getBody().as(ExceptionResponse.class);
        assertThat(exceptionResponse.getMessage())
            .as(ERROR_MESSAGE)
            .contains("Employee ID must be greater than zero");
    }
}
