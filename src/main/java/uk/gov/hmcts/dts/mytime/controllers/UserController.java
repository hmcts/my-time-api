package uk.gov.hmcts.dts.mytime.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.dts.mytime.models.UserModel;
import uk.gov.hmcts.dts.mytime.services.UserService;

import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(
    path = "/User",
    produces = {MediaType.APPLICATION_JSON_VALUE}
)
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable("id") @Valid @Min(value = 1,
        message = "Invalid user ID") int id) {

        return ok(userService.getById(id));
    }

    @PutMapping(path = "/saveUser")
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserModel userModel) {

        return created(URI.create(StringUtils.EMPTY)).body(userService.saveUser(userModel));
    }

    @PatchMapping(path = "/updateUser")
    public ResponseEntity<UserModel> updateUser(@RequestBody @Valid UserModel userModel) {

        return created(URI.create(StringUtils.EMPTY)).body(userService.saveUser(userModel));
    }

    @DeleteMapping(path = "/delete")
    public HttpStatus deleteUser(@RequestBody @Valid UserModel userModel) {

        userService.deleteUser(userModel.getId());

        return HttpStatus.OK;
    }
}
