package uk.gov.hmcts.dts.mytime.models;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.*;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

@Data
@NoArgsConstructor
public class UserModel {
    private String foreName;
    private String sureName;
    private LocalDateTime startDate;
    private Double contractHours;
    private int bonusEntitlement;
    private int managerId;

    public UserModel(Optional<UserEntity> userEntity)
    {
        this.foreName = userEntity.get().getForeName();
        this.sureName = userEntity.get().getSureName();
        this.startDate = userEntity.get().getStartDate();
        this.contractHours = userEntity.get().getContractHours();
        this.bonusEntitlement = userEntity.get().getBonusEntitlement();
        this.managerId = userEntity.get().getId();
    }
}