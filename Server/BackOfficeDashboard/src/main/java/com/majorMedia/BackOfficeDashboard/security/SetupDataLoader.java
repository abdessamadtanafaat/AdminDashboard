package com.majorMedia.BackOfficeDashboard.security;

import com.majorMedia.BackOfficeDashboard.config.EnvironmentUtil;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;

import com.majorMedia.BackOfficeDashboard.repository.PrivilegeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AdminRepository adminRepository;

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository ;

    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Autowired
    public SetupDataLoader(AdminRepository adminRepository,
                           RoleRepository roleRepository,
                           PrivilegeRepository privilegeRepository,
                           PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

    Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE","Description Privilige1");
    Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE","Description Privilige2");

    Set<Privilege> superAdminPrivilegs = new HashSet<>(Arrays.asList(readPrivilege, writePrivilege));
    Set<Privilege> AdminPrivilegs = new HashSet<>(Arrays.asList(readPrivilege));
    Role superAdminRole = createRoleIfNotFound("ROLE_SUPER_ADMIN","Description SUPER ADMIN ROLE", superAdminPrivilegs);
    Role AdminRole = createRoleIfNotFound("ROLE_ADMIN","Descripton ADMIN ROLE", AdminPrivilegs);

        // Fetch environment variables
        String superAdmin1Email = EnvironmentUtil.get("SUPER_ADMIN_1_EMAIL");
        String superAdmin1Password = EnvironmentUtil.get("SUPER_ADMIN_1_PASSWORD");
        String superAdmin2Email = EnvironmentUtil.get("SUPER_ADMIN_2_EMAIL");
        String superAdmin2Password = EnvironmentUtil.get("SUPER_ADMIN_2_PASSWORD");


    Optional<Admin> existingsSuperAdmin1 = adminRepository.findByEmail(superAdmin1Email);
    Optional<Admin> existingsSuperAdmin2 = adminRepository.findByEmail(superAdmin2Email);

    if (!existingsSuperAdmin1.isPresent()){
    Role SuperAdminRole = roleRepository.findByName("ROLE_SUPER_ADMIN");
    Admin admin1 = Admin.builder()
            .email(superAdmin1Email)
            .lastname("Abdessamad")
            .firstname("Tanafaat")
            .password(passwordEncoder.encode(superAdmin1Password))
            .roles( new HashSet<>(Arrays.asList(superAdminRole)))
            .build();
            adminRepository.save(admin1);

    }
        Role SuperAdminRole = roleRepository.findByName("ROLE_SUPER_ADMIN");
        if(!existingsSuperAdmin2.isPresent()){
        Admin admin2 = Admin.builder()
                .email(superAdmin2Email)
                .lastname("ilias")
                .firstname("Rochdi")
                .password(passwordEncoder.encode(superAdmin2Password))
                .roles(new HashSet<>(Arrays.asList(superAdminRole)))
                .build();
        adminRepository.save(admin2);
        }
        alreadySetup =true;
}

    @Transactional
    Privilege createPrivilegeIfNotFound(String name, String description) {

    Privilege privilege = privilegeRepository.findByName(name);
    if(privilege == null){
        privilege = new Privilege(name, description);
        privilegeRepository.save(privilege);

    }
    return privilege;
    }
    @Transactional
    Role createRoleIfNotFound(String name,String descrption, Set<Privilege> privileges){
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name,descrption);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
