package com.majorMedia.BackOfficeDashboard.authentification_module.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    @JsonProperty("access_token")
    private String accessToken;

}
