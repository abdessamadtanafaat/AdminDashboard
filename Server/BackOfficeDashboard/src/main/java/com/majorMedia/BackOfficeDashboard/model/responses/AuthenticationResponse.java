package com.majorMedia.BackOfficeDashboard.model.responses;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
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
    //private List<String> roles;


}


