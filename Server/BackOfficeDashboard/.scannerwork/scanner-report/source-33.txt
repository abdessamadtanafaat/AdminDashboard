package com.majorMedia.BackOfficeDashboard.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "first name is required")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "last name is required")
    private String lastName;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime lastLogin;

    @Column(name = "is_active")
    //@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private boolean isActive;

    @Column(name = "last_logout")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime lastLogout;

    @Column(name = "is_deactivated")
    //@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private boolean is_deactivated ;

    @Column(name = "imagedata")
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private byte[] imageByte;

    @Column(length = 100000)
    private String avatarUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Business> businesses;

}
