package com.majorMedia.BackOfficeDashboard.util;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistToken;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class ServiceUtils {

    private UserRepository userRepository;
    private AdminRepository adminRepository;
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
    public UserResponse mapToUserResponse (User user, String role){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstname(user.getFirstName());
        userResponse.setLastname(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setIs_deactivated(String.valueOf(user.is_deactivated()));
        LocalDate lastLogout = user.getLastLogout().toLocalDate();
        userResponse.setLastLogout(lastLogout);
        userResponse.setRole(role);
        return userResponse;
    }
    public UserResponse mapToUserResponse (Admin admin, String role){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstname(admin.getFirstname());
        userResponse.setLastname(admin.getLastname());
        userResponse.setEmail(admin.getEmail());
        userResponse.setIs_deactivated(String.valueOf(admin.is_deactivated()));
        LocalDate lastLogout = admin.getLastLogout().toLocalDate();
        userResponse.setLastLogout(lastLogout);
        userResponse.setRole(role);

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

}
