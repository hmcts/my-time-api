package uk.gov.hmcts.dts.mytime.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel getById(int id) {

        log.info("Performing query for user by id {}", id);

        UserEntity userEnt = userRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found in Database: " + id));

        UserModel user = new UserModel(Optional.ofNullable(userEnt));

        log.debug("Returned from User table: {}", user);

        return user;
    }

    @Transactional
    public UserModel update(UserModel newUserDetails) {

        log.info("Updating user with {}", newUserDetails);

        return new UserModel();

    }
}
