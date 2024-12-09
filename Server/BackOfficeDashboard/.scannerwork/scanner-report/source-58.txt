package com.majorMedia.BackOfficeDashboard.model.responses;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AdminRolesResponse {
    private Admin admin ;
    private List<Role> predefinedRoles;

}
