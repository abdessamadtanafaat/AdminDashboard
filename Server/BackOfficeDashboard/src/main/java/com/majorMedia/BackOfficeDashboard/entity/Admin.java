package com.majorMedia.BackOfficeDashboard.authentification_module.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue
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

    @Enumerated(EnumType.STRING)
    private Role role;

    private String tokenEmail;

    @Column(name = "expired_time_email")
    private LocalDateTime expiredTimeEmail;

    @Column(name = "is_used_token_email")
    private boolean isUsedTokenEmail;

    @Column(name = "is_active")
    private boolean isActive;

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
