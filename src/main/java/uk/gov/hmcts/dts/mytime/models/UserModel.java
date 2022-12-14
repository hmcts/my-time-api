package uk.gov.hmcts.dts.mytime.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
public class UserModel {
    private String foreName;
    private String surname;
    private LocalDateTime startDate;
    private Double contractHours;
    private int bonusEntitlement;
    private int managerId;

    public UserModel(Optional<UserEntity> userEntity) {
        this.foreName = userEntity.get().getForeName();
        this.surname = userEntity.get().getSurname();
        this.startDate = userEntity.get().getStartDate();
        this.contractHours = userEntity.get().getContractHours();
        this.bonusEntitlement = userEntity.get().getBonusEntitlement();
        this.managerId = userEntity.get().getId();
    }
}
