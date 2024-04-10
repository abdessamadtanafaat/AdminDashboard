package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String avatarUrl;
    private LocalDateTime lastLogout;
    private byte[] imageByte;
    private boolean is_deactivated ;
    private String role;
    private LocalDateTime lastLogin;
    private boolean isActive;


}
