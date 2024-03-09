package com.majorMedia.BackOfficeDashboard.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@RequiredArgsConstructor
public enum Role {
    SUPERADMIN,
    ADMIN/*(
*//*            Set.of(
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_READ,
                    ADMIN_UPDATE
            )*//*)*/;

/*    @Getter
    private final Set<Permission> permissions;*/


    public List<SimpleGrantedAuthority> getAuthorities() {
/*        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;*/
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));

    }
}
