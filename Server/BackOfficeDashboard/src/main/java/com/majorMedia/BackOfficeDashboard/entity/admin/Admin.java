package com.majorMedia.BackOfficeDashboard.entity.admin;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.majorMedia.BackOfficeDashboard.util.ImageUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
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
    private LocalDateTime lastLogin;

    @Column(name = "is_active")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private boolean isActive;

    @Column(name = "is_deactivated")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private boolean is_deactivated ;

    @Column(name = "last_logout")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)

    private LocalDateTime lastLogout;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "admin_role",
            joinColumns = @JoinColumn(
                    name = "admin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))


    private Set<Role> roles;

    @Column(length = 100000)
    private String avatarUrl;

    @Lob
    @Column(name = "imagedata", length = 100000)
    private byte[] imageByte;

    @Column(name = "change_Password_First_Login")
    private boolean changePasswordFirstLogin;


}
