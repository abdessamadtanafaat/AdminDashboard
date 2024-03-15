package com.majorMedia.BackOfficeDashboard.security;

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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

    Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
    Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

    List<Privilege> superAdminPrivilegs = Arrays.asList(readPrivilege, writePrivilege);
    List<Privilege> AdminPrivilegs = Arrays.asList(readPrivilege);
    Role superAdminRole = createRoleIfNotFound("SUPER_ADMIN", superAdminPrivilegs);
    Role AdminRole = createRoleIfNotFound("ADMIN", AdminPrivilegs);

    Optional<Admin> existingsSuperAdmin1 = adminRepository.findByEmail("tanafaat.rca.16@gmail.com");
    Optional<Admin> existingsSuperAdmin2 = adminRepository.findByEmail("ilias.rouchdi21@gmail.com");

    if (!existingsSuperAdmin1.isPresent()){
    Role SuperAdminRole = roleRepository.findByName("SUPER_ADMIN");
    Admin admin1 = Admin.builder()
            .email("tanafaat.rca.16@gmail.com")
            .lastname("Abdessamad")
            .firstname("Tanafaat")
            .password(passwordEncoder.encode("ff"))
            .roles(Arrays.asList((SuperAdminRole)))
            .build();
            adminRepository.save(admin1);

    }
        Role SuperAdminRole = roleRepository.findByName("SUPER_ADMIN");
        if(!existingsSuperAdmin2.isPresent()){
        Admin admin2 = Admin.builder()
                .email("ilias.rouchdi21@gmail.com")
                .lastname("ilias")
                .firstname("Rochdi")
                .password(passwordEncoder.encode("ff"))
                .roles(Arrays.asList((SuperAdminRole)))
                .build();
        adminRepository.save(admin2);
        }
        alreadySetup =true;
}

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

    Privilege privilege = privilegeRepository.findByName(name);
    if(privilege == null){
        privilege = new Privilege(name);
        privilegeRepository.save(privilege);

    }
    return privilege;
    }
    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges){
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
