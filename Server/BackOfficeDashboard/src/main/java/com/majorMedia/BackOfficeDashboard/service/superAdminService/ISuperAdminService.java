package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;

import java.util.Collection;
import java.util.List;

public interface ISuperAdminService {
        public List<UserResponse> getAllAdmins(String sortBy ,String searchKey);
        public Admin createAdmin(CreateAdminRequest createAdminRequest);
        public UserResponse getAdminDetails(Long adminId);
        public String addRole(String name, String description);
        public String assignRoleToAdmin(Long adminId, Long roleId);
        public String addPrivilege(String name, String description);
        public String assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds);
        public String deactivateAccount(Long adminId);
        public String activateAccount(Long adminId);
}



