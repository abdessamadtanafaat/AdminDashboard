package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ISuperAdminService {
        public ObjectsList<Admin> getAllAdmins(String searchKey , String sortBy , int page);
        public Admin createAdmin(Admin createAdminRequest);
        public Admin updateAdmin(Long adminId , Set<Role> roles);
        public AdminRolesResponse getAdminDetails(Long adminId);
        public Role addRole(Role role);
        public Admin assignRoleToAdmin(Long adminId, Long roleId);
        public Privilege addPrivilege(Privilege privilege);
        public RolePrivilegeResponse getRoleDetails(Long roleId);
        public Role assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds);
        public String deactivateAccount(Long adminId);
        public String activateAccount(Long adminId);
        public List<Role> getAllRoles();
        public List<Privilege> getAllPrivileges();

        public Admin revokeRoleFromAdmin(Long adminId, Long roleId);

        public Role revokePrivilegesFromRole(Long roleId, Collection<Long> privilegeIds);
}



