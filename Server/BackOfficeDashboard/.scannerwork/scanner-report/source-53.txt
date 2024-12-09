package com.majorMedia.BackOfficeDashboard.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    private String currentPassword;
    private String password;
    private String email ;
    private String token;
}
