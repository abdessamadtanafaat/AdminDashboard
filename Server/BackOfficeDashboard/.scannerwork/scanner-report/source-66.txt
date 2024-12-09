package com.majorMedia.BackOfficeDashboard.model.responses;

import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RolePrivilegeResponse {
    private Role role ;
    private List<Privilege> predefinedPrivileges;

}
