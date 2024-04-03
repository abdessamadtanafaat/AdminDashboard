package com.majorMedia.BackOfficeDashboard.model.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignRoleRequest {
    private Long adminId;
    private Long roleId;
}
