package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistEmailException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.PermissionsResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.PrivilegeRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final PrivilegeRepository privilegeRepository;
    private final ServiceUtils adminService;

    @Override
    public List<Admin> getAllAdmins(String searchKey  ,String sortBy ,int page ) {
        Pageable paging  = PageRequest.of(page -1 , 5 , Sort.by(Sort.Direction.ASC , "firstname"));

        if(searchKey ==null){
            return adminRepository.findAll(paging).getContent();
        }

        Page<Admin> admins= adminRepository.findAllByFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCase(searchKey ,searchKey , paging);
        return admins.getContent();
    }
    @Override
    @Transactional
    public List<UserResponse> getAllAdmins(String sortBy ,String searchKey) {
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
    public Admin getAdminDetails(Long adminId){
        return  adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

    }
    @Override
    @Transactional
    public Admin createAdmin(CreateAdminRequest createAdminRequest) {
        boolean adminExists = adminRepository.findByEmail(createAdminRequest.getEmail()).isPresent();
        if (adminExists) {
            throw new AlreadyExistEmailException(createAdminRequest.getEmail());
        }


//        String password;
//        if (createAdminRequest.getUsername() != null && !createAdminRequest.getUsername().isEmpty()) {
//            password = createAdminRequest.getUsername();
//        } else {
//            password = passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD);
//        }
//
//        String username;
//        if (createAdminRequest.getUsername() != null && !createAdminRequest.getUsername().isEmpty()) {
//            username = createAdminRequest.getUsername();
//        } else {
//             username = "admin" + createAdminRequest.getLastname().substring(0, Math.min(createAdminRequest.getLastname().length(), 5)).replaceAll("\\s+", "");
//        }
//
//        boolean changePasswordFirstLogin = createAdminRequest.isChangePasswordFirstLogin();
        String password =   RandomStringUtils.randomAlphabetic(10);

        System.out.println(password);

        Admin admin = Admin
                .builder()
                .email(createAdminRequest.getEmail())
                .firstname(createAdminRequest.getFirstname())
                .lastname(createAdminRequest.getLastname())
                .username(createAdminRequest.getUsername())
                .password(passwordEncoder.encode(password))
                .build();
        return adminRepository.save(admin);

    }

    @Override
    @Transactional
    public Role addRole(String nameRole, String descriptionRole){
        if (StringUtils.isEmpty(nameRole) || StringUtils.isEmpty(descriptionRole)) {
            throw new IllegalArgumentException("Name and description are required");
        }

        Role role = new Role(nameRole,descriptionRole);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Privilege addPrivilege(String namePrivilege, String descriptionPrivilege){
        if (StringUtils.isEmpty(namePrivilege) || StringUtils.isEmpty(descriptionPrivilege)) {
            throw new IllegalArgumentException("Name and description are required");
        }
        Privilege privilege = new Privilege(namePrivilege,descriptionPrivilege);
        return  privilegeRepository.save(privilege);


    }

    @Override
    @Transactional
        public Admin assignRoleToAdmin(Long adminId, Long roleId){
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));


        if (adminService.isAdminSuperAdmin(admin)){
            throw new RuntimeException("Cannot assign role to a super admin");
        }

        admin.getRoles().add(role);
        return adminRepository.save(admin);


    }


    @Override
    @Transactional
    public Role assignPrivilegesToRole(Long roleId, Collection<Long> privilegeIds){

        if (privilegeIds.isEmpty() || roleId == null) {
            throw new IllegalArgumentException("Privilege and role are required");
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));

        List<Privilege> privileges = privilegeIds.stream()
                        .map(privilegeId-> privilegeRepository.findById(privilegeId)
                                .orElseThrow(()-> new EntityNotFoundException(privilegeId, Privilege.class)))
                                        .collect(Collectors.toList());

//        role.setPrivileges(privileges);
        role.getPrivileges().addAll(privileges);
        return  roleRepository.save(role);



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

    @Override
    @Transactional
    public List<Role> getAllRoles() {
        List<Role> roles =  roleRepository.findAll();
        return roles;


    }

    @Override
    @Transactional
    public List<Privilege> getAllPrivileges() {
        List<Privilege> privileges =  privilegeRepository.findAll();
        return privileges ;
    }

    @Override
    @Transactional
    public Admin revokeRoleFromAdmin(Long adminId, Long roleId){

        if (adminId == null || roleId == null) {
            throw new IllegalArgumentException("Admin and role are required");
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));

        if (adminService.isAdminSuperAdmin(admin)){
            throw new RuntimeException("Cannot revoke role from a super admin");
        }
        if (!admin.getRoles().contains(role)) {
            throw new RuntimeException("Admin does not have the specified role");
        }
        admin.getRoles().remove(role);
        return adminRepository.save(admin);

    }

    @Override
    @Transactional
    public Role revokePrivilegesFromRole(Long roleId, Collection<Long> privilegeIds){


        if (privilegeIds.isEmpty() || roleId == null) {
            throw new IllegalArgumentException("Privilege and role are required");
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new EntityNotFoundException(roleId, Role.class));

        List<Privilege> privileges = privilegeIds.stream()
                .map(privilegeId-> privilegeRepository.findById(privilegeId)
                        .orElseThrow(()-> new EntityNotFoundException(privilegeId, Privilege.class)))
                .collect(Collectors.toList());


        // Check if role has the privileges to be removed
        for (Long privilegeId : privilegeIds) {
            if (role.getPrivileges().stream().noneMatch(privilege -> privilege.getId().equals(privilegeId))) {
                throw new IllegalArgumentException("Role does not have the specified privilege with ID: " + privilegeId);
            }
        }
                for (Privilege privilege : privileges){
        role.getPrivileges().remove(privilege);
        }
        return roleRepository.save(role);


    }

}


