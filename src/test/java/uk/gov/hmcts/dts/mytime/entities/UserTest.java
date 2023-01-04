package uk.gov.hmcts.dts.mytime.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    private static final Integer ID = 1;

    private static final Integer ID2 = 2;
    private static final String FORENAME = "TestSteve";
    private static final String SURNAME = "TestNewman";
    private static final LocalDateTime DATE_JOINED = LocalDateTime.now(ZoneOffset.UTC);
    private static final Double HOURS = 12.0;
    private static final Integer BONUS_ENTITLEMENT = 2;
    private static final Integer MANAGER_ID = 1;
    private static final Integer MANAGER_ID2 = 2;
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
    void testCreation() {
        assertThat(USER_ENTITY)
            .as("Value does not match")
            .extracting(
                UserEntity::getId,
                UserEntity::getForeName,
                UserEntity::getSurName,
                UserEntity::getStartDate,
                UserEntity::getContractHours,
                UserEntity::getBonusEntitlement,
                UserEntity::getManagerId
            ).containsExactly(
                ID,
                FORENAME,
                SURNAME,
                DATE_JOINED,
                HOURS,
                BONUS_ENTITLEMENT,
                MANAGER_ID
            );
    }

    @Test
    void testEqualsIfSameObject() {
        UserEntity anotherUserEntity = USER_ENTITY;

        assertThat(USER_ENTITY.equals(anotherUserEntity))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testEqualsIfOnlyIdFieldDifferent() {
        UserEntity anotherUserEntity  = new UserEntity(
            ID2,
            FORENAME,
            SURNAME,
            DATE_JOINED,
            HOURS,
            BONUS_ENTITLEMENT,
            MANAGER_ID
        );

        assertThat(USER_ENTITY.equals(anotherUserEntity))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testNotEqualsIfNonIdFieldDifferent() {
        UserEntity anotherUserEntity  = new UserEntity(ID,
                                                       FORENAME,
                                                       SURNAME,
                                                       DATE_JOINED,
                                                       HOURS,
                                                       BONUS_ENTITLEMENT,
                                                       MANAGER_ID2);

        assertThat(USER_ENTITY.equals(anotherUserEntity))
            .as("Equals should not equal")
            .isFalse();
    }

    @Test
    void testHashCodeIfSameObject() {
        UserEntity anotherUserEntity = USER_ENTITY;
        assertThat(USER_ENTITY.hashCode())
            .as("Hashcode should equal")
            .isEqualTo(anotherUserEntity.hashCode());
    }

    @Test
    void testHashCodeIfOnlyIdFieldDifferent() {
        UserEntity anotherUserEntity  = new UserEntity(ID2,
                                                       FORENAME,
                                                       SURNAME,
                                                       DATE_JOINED,
                                                       HOURS,
                                                       BONUS_ENTITLEMENT,
                                                       MANAGER_ID);
        assertThat(USER_ENTITY.hashCode())
            .as("Hashcode should equal")
            .isEqualTo(anotherUserEntity.hashCode());
    }

    @Test
    void testHashCodeIfNonIdFieldDifferent() {
        UserEntity anotherUserEntity  = new UserEntity(ID,
                                                       FORENAME,
                                                       SURNAME,
                                                       DATE_JOINED,
                                                       HOURS,
                                                       BONUS_ENTITLEMENT,
                                                       MANAGER_ID2);
        assertThat(USER_ENTITY.hashCode())
            .as("Hashcode should not equal")
            .isNotEqualTo(anotherUserEntity.hashCode());
    }
}
