package uk.gov.hmcts.dts.mytime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    private int id;
    private String foreName;
    private String surName;
    private LocalDateTime startDate;
    private Double contractHours;
    private int bonusEntitlement;
    private int managerId;

    public UserModel(Optional<UserEntity> userEntity) {
        this.id = userEntity.isPresent() ? userEntity.get().getId() : 0;
        this.foreName = userEntity.isPresent() ? userEntity.get().getForeName() : "";
        this.surName = userEntity.isPresent() ? userEntity.get().getSurName() : "";
        this.startDate = userEntity.isPresent() ? userEntity.get().getStartDate() : LocalDateTime.now();
        this.contractHours = userEntity.isPresent() ? userEntity.get().getContractHours() : 0;
        this.bonusEntitlement = userEntity.isPresent() ? userEntity.get().getBonusEntitlement() : 0;
        this.managerId = userEntity.isPresent() ? userEntity.get().getManagerId() : 0;
    }
}
