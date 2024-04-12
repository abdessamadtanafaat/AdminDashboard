package com.majorMedia.BackOfficeDashboard.util;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.responses.BusinessResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.PermissionsResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.BusinessRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistToken;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.DoubleStream;


@Service
@AllArgsConstructor
public class ServiceUtils {

    private UserRepository userRepository;
    private AdminRepository adminRepository;
    private BusinessRepository businessRepository;
    private BlacklistRepository blacklistRepository;
    private static final Long SUPER_ADMIN_ROLE_ID = 1L;

    public List<User> fetchUsers(String sortBy) {
        return userRepository.findAll(sortByCondition(sortBy));
    }
    public List<Admin> fetchAdmins(String sortBy) {
        return adminRepository.findAll(sortByCondition(sortBy));
    }
    public Sort sortByCondition(String sortBy) {
        if (sortBy == null) {
            return Sort.by("lastLogout").descending();
        }
        return null;
    }

    public Sort sortByConditionBusiness(String sortBy) {
        if (sortBy == null) {
            return Sort.by("createdDate").descending();
        }
        return null;
    }

    public UserResponse mapToUserResponse (User user, String role){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstname(user.getFirstName());
        userResponse.setLastname(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setActive(user.isActive());
        userResponse.set_deactivated(user.is_deactivated());
        LocalDateTime lastLogIn = user.getLastLogin();
        userResponse.setLastLogin(lastLogIn);
        LocalDateTime lastLogout = user.getLastLogout();
        userResponse.setLastLogout(lastLogout);
        userResponse.setRole(role);
        userResponse.setAvatarUrl(user.getAvatarUrl());
        userResponse.setImageByte(user.getImageByte());
        return userResponse;
    }
    public UserResponse mapToUserResponse (Admin admin, String role){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(admin.getId());
        userResponse.setFirstname(admin.getFirstname());
        userResponse.setLastname(admin.getLastname());
        userResponse.setEmail(admin.getEmail());
        userResponse.setActive(admin.isActive());
        userResponse.set_deactivated(admin.is_deactivated());
        LocalDateTime lastLogIn = admin.getLastLogin();
        userResponse.setLastLogin(lastLogIn);
        LocalDateTime lastLogout = admin.getLastLogout();
        userResponse.setLastLogout(lastLogout);
        userResponse.setRole(role);
        userResponse.setAvatarUrl(admin.getAvatarUrl());
        userResponse.setImageByte(admin.getImageByte());

        return userResponse;
    }
    public void addToBlacklist(String jwtToken) {
        BlacklistToken blacklistToken = new BlacklistToken();
        blacklistToken.setToken(jwtToken);
        blacklistRepository.save(blacklistToken);
    }

    public boolean isAdminSuperAdmin(Admin admin) {
        for(Role role : admin.getRoles()){
            if(role.getId().equals(SUPER_ADMIN_ROLE_ID)) { return true; }
        }
        return false;
    }

    public List<Business> fetchBusiness(String sortBy) {
        return businessRepository.findAll(sortByConditionBusiness(sortBy));

    }

    public BusinessResponse mapToBusinessResponse(Business business) {
        BusinessResponse response = new BusinessResponse();
        response.setId(business.getId());
        response.setBusinessName(business.getBusinessName());
        response.setEmail(business.getEmail());
        response.setPhone(business.getPhone());
        response.setAddress(business.getAddress());
        response.setFacebookLink(business.getFacebookLink());
        response.setInstagramLink(business.getInstagramLink());
        response.setGoogleLink(business.getGoogleLink());
        response.setCoverImageUrl(business.getCoverImageUrl());
        response.setCreatedDate(business.getCreatedDate());
        response.setType(business.getType());
        response.setUser(business.getUser());

        return response;
    }

    public PermissionsResponse mapRoleToPermissionResponse(Role role) {
        PermissionsResponse response = new PermissionsResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        return response;
    }
    public PermissionsResponse mapPrivilegeToPermissionResponse(Privilege privilege) {
        PermissionsResponse response = new PermissionsResponse();
        response.setId(privilege.getId());
        response.setName(privilege.getName());
        response.setDescription(privilege.getDescription());
        return response;
    }
}
