package uk.gov.hmcts.dts.mytime.services;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Integer EMPLOYEE_ID = 1;

    private static final UserEntity USER_ENTITY = new UserEntity();

    private static final UserModel USER_MODEL = new UserModel();
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    static void setUp() {
        USER_ENTITY.setStartDate(LocalDateTime.now(ZoneOffset.UTC));
        USER_ENTITY.setForeName("Test");
        USER_ENTITY.setContractHours(37.0);
        USER_ENTITY.setId(EMPLOYEE_ID);
        USER_ENTITY.setManagerId(EMPLOYEE_ID);
        USER_ENTITY.setBonusEntitlement(3);
        USER_ENTITY.setSurName("Tester");
        USER_MODEL.setStartDate(USER_ENTITY.getStartDate());
        USER_MODEL.setForeName(USER_ENTITY.getForeName());
        USER_MODEL.setContractHours(USER_ENTITY.getContractHours());
        USER_MODEL.setId(USER_ENTITY.getId());
        USER_MODEL.setManagerId(USER_ENTITY.getManagerId());
        USER_MODEL.setBonusEntitlement(USER_ENTITY.getBonusEntitlement());
        USER_MODEL.setSurName(USER_ENTITY.getSurName());
    }

    @Test
    void shouldGetUserById() {
        when(userRepo.existsById(EMPLOYEE_ID)).thenReturn(Boolean.TRUE);
        when(userRepo.findById(EMPLOYEE_ID)).thenReturn(Optional.of(USER_ENTITY));

        UserModel res = userService.getById(EMPLOYEE_ID);

        assertThat(res.getId()).isEqualTo(EMPLOYEE_ID);
    }

    @Test
    void shouldThrowNotFoundExceptionGetById() {
        when(userRepo.existsById(EMPLOYEE_ID)).thenReturn(Boolean.FALSE);

        assertThatThrownBy(() -> userService.getById(EMPLOYEE_ID))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("No user found");
    }

    /*@Test
    void shouldSaveUser() {
        when(userRepo.save(USER_ENTITY)).thenReturn(USER_ENTITY);

        UserModel res = userService.saveUser(USER_MODEL);

        assertThat(res.getId()).isEqualTo(EMPLOYEE_ID);
    }*/

    @Test
    void shouldThrowNotFoundExceptionDelete() {
        when(userRepo.existsById(EMPLOYEE_ID)).thenReturn(Boolean.FALSE);

        assertThatThrownBy(() -> userService.deleteUser(EMPLOYEE_ID))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("No user found to delete");
    }
}
