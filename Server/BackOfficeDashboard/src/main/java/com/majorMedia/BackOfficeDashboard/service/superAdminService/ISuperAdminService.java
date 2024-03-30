package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;

import java.util.List;

public interface ISuperAdminService {
        public List<UserResponse> getAllUsers(String sortBy ,String searchKey, String filterByProfile, String filterByStatus);
        public Admin createAdmin(RegisterRequest registerRequest);
        public AdminResponse getAdminDetails(Integer adminId);
}


