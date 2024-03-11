package com.majorMedia.BackOfficeDashboard.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")

    private String email;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")

    private String password;

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String tokenEmail;

    @Column(name = "expired_time_email")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime expiredTimeEmail;

    @Column(name = "is_used_token_email")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private boolean isUsedTokenEmail;

    @Column(name = "last_login")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime lasLogin;

    @Column(name = "is_active")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private boolean isActive;

    @Column(name = "last_logout")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime lastLogout;

    public Admin() {
        this.roles = new ArrayList<>();
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_role",
            joinColumns = @JoinColumn(
                    name = "admin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    //@JsonIgnore
    private Collection<Role> roles;

/*    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
                return role.getAuthorities();
        }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/
}
