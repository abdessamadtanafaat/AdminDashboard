package com.majorMedia.BackOfficeDashboard.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminRequest {
    private String firstname;
    private String lastname;
    private String email;
}
