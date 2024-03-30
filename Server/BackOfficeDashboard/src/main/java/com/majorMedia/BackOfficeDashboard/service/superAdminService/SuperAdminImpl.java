package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistEmailException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.exception.InvalidRoleException;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RoleRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class SuperAdminImpl implements ISuperAdminService {

    private AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ServiceUtils adminService;

    public List<UserResponse> getAllUsers(String sortBy ,String searchKey, String filterByProfile, String filterByStatus) {
        List<UserResponse> userResponses = new ArrayList<>();

        List<User> users = adminService.fetchUsers(sortBy);
        List<Admin> admins = adminService.fetchAdmins(sortBy);


        // Map and combine users and admins
        List<UserResponse> userResponsesFromUsers = users.stream()
                .map(user -> adminService.mapToUserResponse(user, "Proprietaire"))
                .collect(Collectors.toList());

        List<UserResponse> userResponsesFromAdmins = admins.stream()
                .map(admin -> adminService.mapToUserResponse(admin, "Admin"))
                .collect(Collectors.toList());

        userResponses.addAll(userResponsesFromUsers);
        userResponses.addAll(userResponsesFromAdmins);
        userResponses.sort(Comparator.comparing(UserResponse::getLastLogout).reversed());

        if(userResponses.isEmpty()){
            throw new EntityNotFoundException(User.class);
        }

        // Apply filtering by profile if requested
        if (filterByProfile != null && !filterByProfile.isEmpty()) {
            userResponses = userResponses.stream()
                    .filter(userResponse -> userResponse.getProfil().equalsIgnoreCase(filterByProfile))
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(User.class);
            }
        }

        // Apply filtering by status if requested
        if (filterByStatus != null && !filterByStatus.isEmpty()) {
            userResponses = userResponses.stream()
                    .filter(userResponse -> userResponse.getStatus().equalsIgnoreCase(filterByStatus))
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(User.class);
            }
        }

        // Apply filtering by profile if requested
        if (searchKey != null && !searchKey.isEmpty()) {
            userResponses = userResponses.stream()
                    .filter(userResponse -> userResponse.getFirstname().contains(searchKey) || userResponse.getLastname().contains(searchKey)|| userResponse.getEmail().contains(searchKey))
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(searchKey, User.class);
            }
        }

        if (userResponses.isEmpty() && admins.isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }

        return userResponses;
    }
    @Override
    public AdminResponse getAdminDetails(Integer adminId){
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        AdminResponse adminResponse = AdminResponse.builder()
                .id(admin.getId())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .email(admin.getEmail())
                .avatarUrl(admin.getAvatarUrl())
                .build();

        return adminResponse;
    }
    @Transactional
    public Admin createAdmin(RegisterRequest registerRequest) {
        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if (adminExists) {
            throw new AlreadyExistEmailException(registerRequest.getEmail());
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                .firstname(registerRequest.getFirstname()).
                build();

        admin.setPassword(encodedPassword);

        Collection<Role> roles = new ArrayList<>();
        for (RoleRequest roleRequest : registerRequest.getRoles()) {
            Role role = roleRepository.findByName(roleRequest.getName());
            if (role == null) {
                throw new InvalidRoleException(roleRequest.getName());
            }
            roles.add(role);
        }
        admin.setRoles(roles);

        return adminRepository.save(admin);

    }






}

