package uk.gov.hmcts.dts.rsecheck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.dts.mytime.controllers.UserController;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserController.class)
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
class UserControllerTest {

    private static final String BASE_URL = "/User";

    @MockBean
    private UserService userService;

    @Autowired
    private transient MockMvc mockMvc;

    @Test
    void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL + "/0"))
            .andExpect(status().isBadRequest())
            .andReturn();
    }

    @Test
    void shouldReturnNullUserObject () throws  Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
            .andExpect(status().isNoContent())
            .andReturn();
    }

    @Test
    void shouldReturnUserObject() throws Exception {

        UserEntity userEnt = new UserEntity(
            1,
            "TestSteve",
            "TestNewman",
            LocalDateTime.now(ZoneOffset.UTC),
            12.0,
            2,
            1 );

        UserModel userMod = new UserModel(Optional.of(userEnt));

        when(userService.getById(1)).thenReturn(userMod);

        MvcResult mvcResult = mockMvc.perform(get(BASE_URL + "/1"))
            .andExpect(status().isOk())
            .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        UserModel returnedModel = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserModel.class);
        assertThat(returnedModel).isEqualTo(userMod);
    }

//    @Test
//    void shouldFindCourtByQuery() throws Exception {
//
//        final Path path = Paths.get("src/test/resources/nspl_address_example.json");
//        final String expectedJson = new String(readAllBytes(path));
//
//        final NsplAddress nsplAddress = new ObjectMapper().readValue(path.toFile(), NsplAddress.class);
//        final String query = "PL51AA";
//
//        when(nsplService.getAddressInfo(query)).thenReturn(nsplAddress);
//
//        mockMvc.perform(get(BASE_URL + query))
//            .andExpect(status().isOk())
//            .andExpect(content().json(expectedJson))
//            .andReturn();
//    }

//    @Test
//    void shouldUseGlobalExceptionHandler() throws Exception {
//
//        final String query = "PL51AA";
//
//        when(userService.getYser(query)).thenDoNothing(new NotFoundException(query));
//
//        try {
//            mockMvc.perform(get(BASE_URL + query)).andExpect(status().isNotFound());
//        } catch (NestedServletException e) {
//            assertThrows(NotFoundException.class, () -> {
//                throw e.getCause();
//            });
//            assertThat(e.getMessage())
//                .containsPattern("uk.gov.hmcts.reform.rpts.exceptions.NotFoundException: Not found: PL51AA");
//        }
//    }
}
