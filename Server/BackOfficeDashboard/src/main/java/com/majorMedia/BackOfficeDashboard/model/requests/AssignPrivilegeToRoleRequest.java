package com.majorMedia.BackOfficeDashboard.model.requests;

import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignPrivilegeToRoleRequest {
    Long roleId;
    Collection<Long> privilegeIds;
}
