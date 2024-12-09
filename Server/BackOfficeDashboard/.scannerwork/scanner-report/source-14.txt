package com.majorMedia.BackOfficeDashboard.entity.admin;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@Entity
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Admin> admins;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))

    private Set<Privilege> privileges;

    public Role() {}

    public Role(String name,String description) {
        this.name = name;
        this.description = description;
    }

}