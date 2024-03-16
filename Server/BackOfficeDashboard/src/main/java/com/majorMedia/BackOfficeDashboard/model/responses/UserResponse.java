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
    private String fullname;
    private String email;
    private String status;
    private String phone;
    private LocalDate createdAt;

}
