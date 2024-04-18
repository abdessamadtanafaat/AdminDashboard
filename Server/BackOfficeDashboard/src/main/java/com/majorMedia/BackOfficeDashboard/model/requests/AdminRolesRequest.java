package com.majorMedia.BackOfficeDashboard.model.requests;

import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminRolesRequest {
    private Long adminId;
    private Set<Role> roles;
}
