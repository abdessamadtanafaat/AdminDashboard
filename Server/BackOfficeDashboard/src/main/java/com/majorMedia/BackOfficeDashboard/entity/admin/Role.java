package com.majorMedia.BackOfficeDashboard.entity.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/*@RequiredArgsConstructor
public enum Role {
    SUPER_ADMIN,
    ADMIN
(

            Set.of(
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_READ,
                    ADMIN_UPDATE
            )
)
;

    @Getter
    private final Set<Permission> permissions;



    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;

        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));

    }
}*/

@AllArgsConstructor
@Entity

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<Admin> admins;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))

            private Collection<Privilege> privileges;


    public Role() {}

    public Role(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }
}