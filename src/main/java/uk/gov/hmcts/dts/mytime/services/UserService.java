package uk.gov.hmcts.dts.mytime.services;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;
import uk.gov.hmcts.dts.mytime.exceptions.NotFoundException;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.repository.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserModel getById(int id) {

        if (!userRepo.existsById(id)) {
            throw new NotFoundException("No user found");
        }
        return new UserModel(userRepo.findById(id));
    }

    public void saveUser(UserModel userModel) {

        final UserEntity userEntity = new UserEntity(userModel);

        userRepo.save(userEntity);

    }

    public void deleteUser(int id) {

        if (!userRepo.existsById(id)) {
            throw new NotFoundException("No user found to delete");
        }

        userRepo.deleteById(id);
    }
}
