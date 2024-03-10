package com.majorMedia.BackOfficeDashboard.Security.Manager;

import com.majorMedia.BackOfficeDashboard.entity.Admin;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private AdminService adminService;
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Admin admin = adminService.findByEmail(authentication.getName());

        if(!passwordEncoder.matches(authentication.getCredentials().toString(),admin.getPassword())){
            throw new BadCredentialsException("You provided an incorrect password");
        }
        admin.setActive(true);
        adminRepository.save(admin);
        return new UsernamePasswordAuthenticationToken(authentication.getName(), admin.getPassword());

    }
}
