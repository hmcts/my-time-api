package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {
}
