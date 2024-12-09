package com.majorMedia.BackOfficeDashboard.entity.campaign;
import jakarta.persistence.*;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoyaltyProgramme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;
    private int age;
    private String mysteryBoxPrize;
    private String couponCode;
    private int npsValue;
    private String mbPriceCondition;
    private double couponValue;
    @ManyToOne
    @JoinColumn(name = "compaign_id", nullable = false)
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "loyalty_programme_type_id", nullable = false)
    private LoyaltyProgrammeType loyaltyProgrammeType;

}
