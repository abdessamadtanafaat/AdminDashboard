package com.majorMedia.BackOfficeDashboard.authentification_module.Security.Manager;

import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder Passwordencoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Admin admin = adminService.findByEmail(authentication.getName());
        if(!Passwordencoder.matches(authentication.getCredentials().toString(),admin.getPassword()){
            throw new BadCredentialsException("You provided an incorrect password");

        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), admin.getPassword());

    }
}
