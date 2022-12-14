package uk.gov.hmcts.dts.mytime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
}
