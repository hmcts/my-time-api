package uk.gov.hmcts.dts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;
import uk.gov.hmcts.dts.mytime.models.UserModel;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(SpringExtension.class)
class UserControllerTest extends FunctionalTestBase {

    private static final String PATH = "/User";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int EMPLOYEE_ID1 = 1;

    @BeforeAll
    static void setup() {
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @Test
    void shouldGetUserById() {
        var response = doGetRequest(PATH + "/1");
        assertThat(response.statusCode()).isEqualTo(200);

        UserModel userModel = response.getBody().as(UserModel.class);
        assertThat(userModel.getId()).isEqualTo(EMPLOYEE_ID1);
    }

    @Test
    void shouldSaveNewUser() throws JsonProcessingException {

        UserModel saveNewUserUserModel = new UserModel(
            ThreadLocalRandom.current().nextInt(),
            "Func",
            "Tional",
            LocalDateTime.now(),
            12.0,
            5,
            0
        );

        String jsonInput = OBJECT_MAPPER.writeValueAsString(saveNewUserUserModel);

        var response = doPutRequest(PATH + "/saveUser", jsonInput);

        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.getBody().as(UserModel.class).getForeName()).isEqualTo(saveNewUserUserModel.getForeName());
    }

    @Test
    void shouldUpdateUser() throws JsonProcessingException {

        UserModel modelToUpdate = doGetRequest(PATH + "/1").as(UserModel.class);

        modelToUpdate.setForeName("ShouldUpdateUser_forename");

        String jsonInput = OBJECT_MAPPER.writeValueAsString(modelToUpdate);

        var response = doPutRequest(PATH + "/saveUser", jsonInput);

        assertThat(response.statusCode()).isEqualTo(CREATED.value());
        assertThat(response.getBody().as(UserModel.class).getForeName()).isEqualTo("ShouldUpdateUser_forename");

    }

    @Test
    void shouldDeleteUser() throws JsonProcessingException {

        var saveNewUserUserModel = new UserModel(
            ThreadLocalRandom.current().nextInt(),
            "User To",
            "Delete",
            LocalDateTime.now(),
            12.0,
            5,
            0
        );

        String jsonInput = OBJECT_MAPPER.writeValueAsString(saveNewUserUserModel);

        var response = doPutRequest(PATH + "/saveUser", jsonInput);

        var savedUser = response.getBody().as(UserModel.class);

        jsonInput = OBJECT_MAPPER.writeValueAsString(savedUser);

        response = doDeleteRequest(PATH + "/delete", jsonInput);

        assertThat(response.statusCode()).isEqualTo(200);
    }
}
