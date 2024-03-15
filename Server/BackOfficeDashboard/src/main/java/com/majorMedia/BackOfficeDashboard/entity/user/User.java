package com.majorMedia.BackOfficeDashboard.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private LocalDateTime createdAt;

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
}
