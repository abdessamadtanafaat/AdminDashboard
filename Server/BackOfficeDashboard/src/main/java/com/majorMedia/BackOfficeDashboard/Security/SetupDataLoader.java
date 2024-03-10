package com.majorMedia.BackOfficeDashboard.Security;

import com.majorMedia.BackOfficeDashboard.entity.Admin;
import com.majorMedia.BackOfficeDashboard.entity.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.Role;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;

import com.majorMedia.BackOfficeDashboard.repository.PrivilegeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
    Role role = createRoleIfNotFound("SUPER_ADMIN", superAdminPrivilegs);
    Role role2 = createRoleIfNotFound("ADMIN", AdminPrivilegs);

    Role adminRole = roleRepository.findByName("SUPER_ADMIN");

    Admin admin1 = Admin.builder()
            .email("tanafaat.rca.16@gmail.com")
            .lastname("Abdessamad")
            .firstname("Tanafaat")
            .password(passwordEncoder.encode("ff"))
            .roles(Arrays.asList((adminRole)))
            //.enabled(true);
            .build();
            alreadySetup =true;
            adminRepository.save(admin1);

        Admin admin2 = Admin.builder()
                .email("ilias.rouchdi21@gmail.com")
                .lastname("ilias")
                .firstname("Rochdi")
                .password(passwordEncoder.encode("ff"))
                .roles(Arrays.asList((adminRole)))
                //.enabled(true);
                .build();
        alreadySetup =true;
        adminRepository.save(admin2);
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
