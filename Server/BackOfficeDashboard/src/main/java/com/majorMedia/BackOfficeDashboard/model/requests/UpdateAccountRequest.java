package com.majorMedia.BackOfficeDashboard.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
    private String email;
    private String firstname;
    private String lastname;
    private String jwtToken;
}
