package uk.gov.hmcts.dts.mytime.models;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class UserTest {

    private static final Integer ID = 1;
    private static final String FORENAME = "TestSteve";
    private static final String SURNAME = "TestNewman";
    private static final LocalDateTime DATE_JOINED = LocalDateTime.now(ZoneOffset.UTC);
    private static final Double HOURS = 12.0;
    private static final Integer BONUS_ENTITLEMENT = 2;
    private static final Integer MANAGER_ID = 1;
    private static final UserEntity USER_ENTITY = new UserEntity(
        ID,
        FORENAME,
        SURNAME,
        DATE_JOINED,
        HOURS,
        BONUS_ENTITLEMENT,
        MANAGER_ID
    );

    @Test
    void testCreationWithEntity() {
        UserModel userModel = new UserModel(Optional.of(USER_ENTITY));

        assertThat(userModel)
            .as("Value does not match")
            .extracting(
                UserModel::getId,
                UserModel::getForeName,
                UserModel::getSurName,
                UserModel::getStartDate,
                UserModel::getContractHours,
                UserModel::getBonusEntitlement,
                UserModel::getManagerId
            )
            .containsExactly(
                ID,
                FORENAME,
                SURNAME,
                DATE_JOINED,
                HOURS,
                BONUS_ENTITLEMENT,
                MANAGER_ID
            );
    }
}
