package uk.gov.hmcts.dts.mytime.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.gov.hmcts.dts.mytime.models.UserModel;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Getter
@Setter
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class UserEntity {
    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    private Integer id;
    @Column(nullable = false, name = "forename")
    private String foreName;
    @Column(nullable = false, name = "surname")
    private String surName;
    @Column(nullable = false, name = "startdate")
    private LocalDateTime startDate;
    @Column(nullable = false, name = "contracthours")
    private Double contractHours;

    @Column(name = "bonusentitlement")
    private Integer bonusEntitlement;

    @Column(name = "managerid")
    private Integer managerId;

    public UserEntity() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public UserEntity(UserModel userModel) {
        this.id = userModel.getId();
        this.foreName = userModel.getForeName();
        this.surName = userModel.getSurName();
        this.startDate = userModel.getStartDate();
        this.contractHours = userModel.getContractHours();
        this.bonusEntitlement = userModel.getBonusEntitlement();
        this.managerId = userModel.getManagerId();
    }

    public UserEntity(
        int id,
        String foreName,
        String sureName,
        LocalDateTime startDate,
        double hours,
        int bonusEntitlement,
        int managerId) {
        this.id = id;
        this.foreName = foreName;
        this.surName = sureName;
        this.startDate = startDate;
        this.contractHours = hours;
        this.bonusEntitlement = bonusEntitlement;
        this.managerId = managerId;
    }
}
