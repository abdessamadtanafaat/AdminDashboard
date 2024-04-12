package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistEmailException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.PrivilegeRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
    public UserResponse getAdminDetails(Long adminId){
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        UserResponse userResponse = UserResponse.builder()
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .email(admin.getEmail())
                .avatarUrl(admin.getAvatarUrl())
                .lastLogout(admin.getLastLogout())
                .imageByte(admin.getImageByte())
                .avatarUrl(admin.getAvatarUrl())
                .is_deactivated(admin.is_deactivated())
                .lastLogin(admin.getLastLogin())
                .role(admin.getRoles().toString())
                .build();
        return userResponse;
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
    public String addRole(String nameRole, String descriptionRole){
        if (StringUtils.isEmpty(nameRole) || StringUtils.isEmpty(descriptionRole)) {
            throw new IllegalArgumentException("Name and description are required");
        }

        Role role = new Role(nameRole,descriptionRole);
        roleRepository.save(role);
        return  "Role Created Successfully";
    }

    @Override
    @Transactional
    public String addPrivilege(String namePrivilege, String descriptionPrivilege){
        if (StringUtils.isEmpty(namePrivilege) || StringUtils.isEmpty(descriptionPrivilege)) {
            throw new IllegalArgumentException("Name and description are required");
        }
        Privilege privilege = new Privilege(namePrivilege,descriptionPrivilege);
         privilegeRepository.save(privilege);
            return  "Privilege Created Successfully";

    }

    @Override
    @Transactional
        public String assignRoleToAdmin(Long adminId, Long roleId){

        if (adminId == null || roleId == null) {
            throw new IllegalArgumentException("Admin and role are required");
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));


        if (adminService.isAdminSuperAdmin(admin)){
            throw new RuntimeException("Cannot assign role to a super admin");
        }
        admin.getRoles().add(role);
        adminRepository.save(admin);

        return "Role Assigned Successfully To " +admin.getLastname() +" Successfully" ;
    }


    @Override
    @Transactional
    public String assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds){

        if (privilegeIds.isEmpty() || roleId == null) {
            throw new IllegalArgumentException("Privilege and role are required");
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));

        List<Privilege> privileges = privilegeIds.stream()
                        .map(privilegeId-> privilegeRepository.findById(privilegeId)
                                .orElseThrow(()-> new EntityNotFoundException(privilegeId, Privilege.class)))
                                        .collect(Collectors.toList());

        role.setPrivileges(privileges);
        roleRepository.save(role);

        return "Privileges Assigned To "+ role.getName()+" Successfully ";

    }

    @Override
    @Transactional
    public String deactivateAccount(Long adminId){
        if (adminId == null) {
            throw new IllegalArgumentException("Admin are required");
        }

        Admin admin  = adminRepository.findById(adminId)
                .orElseThrow(()->new EntityNotFoundException(adminId, Admin.class));

            if (adminService.isAdminSuperAdmin(admin)){
                throw new RuntimeException("Impossible to deactivate the super admin account");
            }


        admin.set_deactivated(true);
        adminRepository.save(admin);
        return "Account : "+admin.getLastname() +" deactivated Successfully";
        }

    @Override
    @Transactional
    public String activateAccount(Long adminId) {
        if (adminId == null) {
            throw new IllegalArgumentException("Admin are required");
        }
        Admin admin  = adminRepository.findById(adminId)
                .orElseThrow(()->new EntityNotFoundException(adminId, Admin.class));

        admin.set_deactivated(false);
        adminRepository.save(admin);
        return "Account : "+admin.getLastname() +" activated Successfully";
    }
}

