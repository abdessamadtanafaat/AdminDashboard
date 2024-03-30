package com.majorMedia.BackOfficeDashboard.util;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
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
    public UserResponse mapToUserResponse (User user, String profile){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstname(user.getFirstName());
        userResponse.setLastname(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setStatus(user.getStatus());
        LocalDate lastLogout = user.getLastLogout().toLocalDate();
        userResponse.setLastLogout(lastLogout);
        userResponse.setProfil(profile);
        return userResponse;
    }
    public UserResponse mapToUserResponse (Admin admin, String profile){
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstname(admin.getFirstname());
        userResponse.setLastname(admin.getLastname());
        userResponse.setEmail(admin.getEmail());
        userResponse.setStatus(admin.getStatus());
        LocalDate lastLogout = admin.getLastLogout().toLocalDate();
        userResponse.setLastLogout(lastLogout);
        userResponse.setProfil(profile);

        return userResponse;
    }
}
