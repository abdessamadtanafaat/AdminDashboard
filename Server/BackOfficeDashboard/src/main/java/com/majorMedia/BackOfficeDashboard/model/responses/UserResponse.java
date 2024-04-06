package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String is_deactivated;
    private LocalDate lastLogout;
    private String role;

}
