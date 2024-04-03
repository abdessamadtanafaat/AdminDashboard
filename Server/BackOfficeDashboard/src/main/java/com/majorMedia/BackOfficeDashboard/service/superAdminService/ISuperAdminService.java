package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;

import java.util.Collection;
import java.util.List;

public interface ISuperAdminService {
        public List<UserResponse> getAllUsers(String sortBy ,String searchKey, String filterByProfile, String filterByStatus);
        public Admin createAdmin(RegisterRequest registerRequest);
        public AdminResponse getAdminDetails(Long adminId);
        public Role addRole(String name, String description);
        public Admin assignRoleToAdmin(Long adminId, Long roleId);
        public Privilege addPrivilege(String name, String description);
        public String assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds);

}



