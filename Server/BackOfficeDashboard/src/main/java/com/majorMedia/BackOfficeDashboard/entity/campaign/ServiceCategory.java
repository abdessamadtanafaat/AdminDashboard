package com.majorMedia.BackOfficeDashboard.entity.campaign;


import jakarta.persistence.*;
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
    private String name;
    private String description;
    @OneToMany(mappedBy = "serviceCategory" ,  orphanRemoval = true , cascade =  CascadeType.ALL)
    private Set<ServiceArea> serviceAreas;
}