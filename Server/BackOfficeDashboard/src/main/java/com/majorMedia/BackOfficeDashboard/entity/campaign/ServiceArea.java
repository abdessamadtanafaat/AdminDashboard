package com.majorMedia.BackOfficeDashboard.entity.campaign;

import com.majorMedia.BackOfficeDashboard.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isPrivate;
    @ManyToOne
    @JoinColumn(name = "service_category_id")
    private ServiceCategory serviceCategory;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
