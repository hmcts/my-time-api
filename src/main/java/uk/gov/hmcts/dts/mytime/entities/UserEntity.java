package uk.gov.hmcts.dts.mytime.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String foreName;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private Double contractHours;

    private int bonusEntitlement;
    private int managerId;
}
