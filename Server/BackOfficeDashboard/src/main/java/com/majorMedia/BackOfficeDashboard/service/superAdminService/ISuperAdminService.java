package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.PermissionsResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;

import java.util.Collection;
import java.util.List;

public interface ISuperAdminService {
        public List<Admin> getAllAdmins();
        public List<UserResponse> getAllAdmins(String sortBy ,String searchKey);
        public Admin createAdmin(CreateAdminRequest createAdminRequest);
        public UserResponse getAdminDetails(Long adminId);
        public Role addRole(String name, String description);
        public Admin assignRoleToAdmin(Long adminId, Long roleId);
        public Privilege addPrivilege(String name, String description);
        public Role assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds);
        public String deactivateAccount(Long adminId);
        public String activateAccount(Long adminId);
        public List<Role> getAllRoles();
        public List<Privilege> getAllPrivileges();

        public Admin revokeRoleFromAdmin(Long adminId, Long roleId);

        public Role revokePrivilegesFromRole(Long roleId, Collection<Long> privilegeIds);
}



