package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistEmailException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.exception.SuperAdminRoleAssignmentException;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.PrivilegeRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class SuperAdminImpl implements ISuperAdminService {

    private AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final ServiceUtils adminService;


    @Override
    @Transactional
    public List<UserResponse> getAllUsers(String sortBy ,String searchKey) {
        List<UserResponse> userResponses = new ArrayList<>();

        List<Admin> admins = adminService.fetchAdmins(sortBy);

        List<UserResponse> userResponsesFromAdmins = admins.stream()
                .map(admin -> {
                    String role = "";
                    for (Role adminRole : admin.getRoles()) {
                        if (adminRole.getName().equals("ROLE_SUPER_ADMIN")) {
                            role = "Super Admin";
                            break;
                        } else if (adminRole.getName().equals("ROLE_ADMIN")) {
                            role = "Admin";
                            break;
                        }
                    }
                    return adminService.mapToUserResponse(admin, role);
                })
                .collect(Collectors.toList());


        userResponses.addAll(userResponsesFromAdmins);
        userResponses.sort(Comparator.comparing(UserResponse::getLastLogout).reversed());

        if(userResponses.isEmpty()){
            throw new EntityNotFoundException(User.class);
        }

/*        // Apply filtering by profile if requested
        if (filterByProfile != null && !filterByProfile.isEmpty()) {
            userResponses = userResponses.stream()
                    .filter(userResponse -> userResponse.getRole().equalsIgnoreCase(filterByProfile))
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(User.class);
            }
        }*/

        // Apply filtering by searchKey if requested
        if (searchKey != null && !searchKey.isEmpty()) {
            userResponses = userResponses.stream()
                    .filter(userResponse -> userResponse.getFirstname().contains(searchKey) || userResponse.getLastname().contains(searchKey)|| userResponse.getEmail().contains(searchKey))
                    .collect(Collectors.toList());
            if (userResponses.isEmpty()) {
                throw new EntityNotFoundException(searchKey, User.class);
            }
        }

        return userResponses;
    }
    @Override
    @Transactional
    public AdminResponse getAdminDetails(Long adminId){
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
    @Override
    @Transactional
    public Admin createAdmin(CreateAdminRequest createAdminRequest) {
        boolean adminExists = adminRepository.findByEmail(createAdminRequest.getEmail()).isPresent();
        if (adminExists) {
            throw new AlreadyExistEmailException(createAdminRequest.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD);
        Admin admin = Admin.builder()
                .email(createAdminRequest.getEmail())
                .lastname(createAdminRequest.getLastname())
                .firstname(createAdminRequest.getFirstname())
                .password(encodedPassword)
                .build();

        //admin.setPassword(encodedPassword);

        return adminRepository.save(admin);

    }

    @Override
    @Transactional
    public Role addRole(String name, String description){
        Role role = new Role(name,description);
        return  roleRepository.save(role);
    }

    @Override
    @Transactional
    public Privilege addPrivilege(String name, String description){
        Privilege privilege = new Privilege(name,description);
        return privilegeRepository.save(privilege);
    }

    @Override
    @Transactional
    public Admin assignRoleToAdmin(Long adminId, Long roleId){

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));


        if (adminService.isAdminSuperAdmin(admin)){
            throw new SuperAdminRoleAssignmentException();
        }
        admin.getRoles().add(role);
        adminRepository.save(admin);

        return admin;
    }


    @Override
    @Transactional
    public String assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds){

        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));

        List<Privilege> privileges = privilegeIds.stream()
                        .map(privilegeId-> privilegeRepository.findById(privilegeId)
                                .orElseThrow(()-> new EntityNotFoundException(privilegeId, Privilege.class)))
                                        .collect(Collectors.toList());

        role.setPrivileges(privileges);
        roleRepository.save(role);

        return "Privilege Assigned Successfully";

    }

    @Override
    @Transactional
    public String deactivateAccount(Long adminId){
        Admin admin  = adminRepository.findById(adminId)
                .orElseThrow(()->new EntityNotFoundException(adminId, Admin.class));

        admin.set_deactivated(true);
        adminRepository.save(admin);
        return "Account deactivated Successfully";
        }

    @Override
    @Transactional
    public String activateAccount(Long adminId) {
        Admin admin  = adminRepository.findById(adminId)
                .orElseThrow(()->new EntityNotFoundException(adminId, Admin.class));

        admin.set_deactivated(false);
        adminRepository.save(admin);
        return "Account activated Successfully";
    }
}


