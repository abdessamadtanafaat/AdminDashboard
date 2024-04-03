package com.majorMedia.BackOfficeDashboard.entity.admin;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter
@Setter

public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

   @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege() {}
    public Privilege(String name, String description) {
        this.name = name;
        this.description = description;
    }


}
