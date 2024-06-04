package com.majorMedia.BackOfficeDashboard.service.superAdminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistEmailException;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistRoleException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.requests.CreateAdminRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.*;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.PrivilegeRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
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
    private final EmailUtils emailUtils;

    @Override
    public ObjectsList<Admin> getAllAdmins(String searchKey  ,String sortBy ,int page ) {
        Pageable paging  = PageRequest.of(page -1 , 5 , Sort.by(Sort.Direction.ASC , "firstname"));
        if(searchKey ==null){
            return unwrapAdminList(adminRepository.findAll(paging) , page);
        }
        Page<Admin> admins= adminRepository.findAllByFirstnameContainsIgnoreCaseOrLastnameContainsIgnoreCase(searchKey ,searchKey , paging);
        return unwrapAdminList(admins , page);
    }
    public ObjectsList<Admin> unwrapAdminList(Page<Admin> admins , int page){
        return ObjectsList.<Admin>builder().data(admins.getContent()).
                meta(
                        new PaginationData(
                                page , 5 , admins.getTotalPages() ,
                                admins.getTotalElements()
                               )).build();

    }

    @Override
    @Transactional
    public AdminRolesResponse getAdminDetails(Long adminId){
        Admin admin  =  adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));
        List<Role> allRoles = getAllRoles();
        Set<Role> adminRoles = admin.getRoles();

        // Filter allRoles from adminRoles
        List<Role> filteredRoles = allRoles.stream()
                .filter(role -> !adminRoles.contains(role))
                .collect(Collectors.toList());

        return new AdminRolesResponse(admin, filteredRoles);
    }
    @Override
    @Transactional
    public Admin createAdmin(Admin createAdminRequest) throws MessagingException, UnsupportedEncodingException {
        boolean adminExists = adminRepository.findByEmail(createAdminRequest.getEmail()).isPresent();
        if (adminExists) {
            throw new AlreadyExistEmailException(createAdminRequest.getEmail());
        }




          String password =   RandomStringUtils.randomAlphabetic(10);

          System.out.println(password);
          createAdminRequest.setPassword(passwordEncoder.encode(password));
          createAdminRequest.setJoined_in(LocalDateTime.now());
          emailUtils.sendEmailToAdmin(createAdminRequest.getEmail() , password);
          return adminRepository.save(createAdminRequest);

    }

    @Override
    public Admin updateAdmin(Long adminId ,Set<Role> roles ) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));
        admin.setRoles(roles);
        return adminRepository.save(admin);

    }

    @Override
    @Transactional
    public Role addRole(Role role){
        if (StringUtils.isEmpty(role.getName()) || StringUtils.isEmpty(role.getDescription())) {
            throw new IllegalArgumentException("Name and description are required");
        }
        boolean roleExists = roleRepository.findByNameIgnoreCase(role.getName()).isPresent();
        if (roleExists) {
            throw new AlreadyExistRoleException(role.getName());
        }
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Privilege addPrivilege(Privilege privilege){
        if (StringUtils.isEmpty(privilege.getName()) || StringUtils.isEmpty(privilege.getDescription())) {
            throw new IllegalArgumentException("Name and description are required");
        }
        boolean privilegeExists = privilegeRepository.findByNameIgnoreCase(privilege.getName()).isPresent();
        if (privilegeExists) {
            throw new AlreadyExistRoleException(privilege.getName());
        }
        return  privilegeRepository.save(privilege);


    }

    @Override
    public RolePrivilegeResponse getRoleDetails(Long roleId) {
        Role role  =  roleRepository.findById(roleId)
                .orElseThrow(()-> new EntityNotFoundException(roleId, Admin.class));
        List<Privilege> privileges = getAllPrivileges();
        Set<Privilege> rolePrivileges = role.getPrivileges();

        // Filter allRoles from adminRoles
        List<Privilege> filteredPrivileges = privileges.stream()
                .filter(privilege -> !rolePrivileges.contains(privilege))
                .collect(Collectors.toList());

        return new RolePrivilegeResponse(role, filteredPrivileges);
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

    public List<Admin> getAllPagesAdmins() {
        return adminRepository.findAll();
    }
}


