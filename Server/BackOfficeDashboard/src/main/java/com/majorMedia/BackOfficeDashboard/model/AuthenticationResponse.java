package com.majorMedia.BackOfficeDashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.majorMedia.BackOfficeDashboard.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private String jwt;
    private Admin admin ;

}
