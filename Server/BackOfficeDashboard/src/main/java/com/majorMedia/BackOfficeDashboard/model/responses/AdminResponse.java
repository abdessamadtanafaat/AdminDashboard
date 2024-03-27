package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;

    private byte[] imageData;
}