package com.majorMedia.BackOfficeDashboard.entity.business;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BusinessCategory")
public class BusinessCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String categoryName;
    @NotEmpty(message = "Description cannot be empty")
    @Column(length = 10000000)
    private String categoryDesc;
    private String photoUrl;
    @OneToMany(mappedBy = "businessCategory",orphanRemoval = true , cascade = CascadeType.ALL)
    private List<BusinessType> businessTypes;
}
