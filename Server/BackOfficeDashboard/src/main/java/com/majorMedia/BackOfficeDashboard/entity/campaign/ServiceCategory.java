package com.majorMedia.BackOfficeDashboard.entity.campaign;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="Name cannot be empty")

    private String name;
    @NotEmpty(message="Description cannot be empty")

    private String description;
    private String photoUrl;

    @OneToMany(mappedBy = "serviceCategory" ,  orphanRemoval = true , cascade =  CascadeType.ALL)
    private Set<ServiceArea> serviceAreas;
}