package com.majorMedia.BackOfficeDashboard.security.manager;

import com.majorMedia.BackOfficeDashboard.exception.AccountDeactivatedException;
import com.majorMedia.BackOfficeDashboard.exception.NotFoundEmailException;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;

import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistToken;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    private BlacklistRepository blacklistRepository;
    private ServiceUtils serviceUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Admin admin = adminRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundEmailException(authentication.getName()))        ;

        if (admin.is_deactivated()){
            throw new AccountDeactivatedException();
        }
        if(!passwordEncoder.matches(authentication.getCredentials().toString(),admin.getPassword())){
            throw new BadCredentialsException("You provided an incorrect password");
        }
        admin.setActive(true);
        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);
        return new UsernamePasswordAuthenticationToken(authentication.getName(), admin.getPassword());
    }

    public String logout(String email, String jwtToken) {

        try{
            Optional<Admin> adminOptional = adminRepository.findByEmail(email);
            Admin admin = adminOptional.orElseThrow(() -> new NotFoundEmailException(email));

            if(!blacklistRepository.existsByToken(jwtToken)) {
                serviceUtils.addToBlacklist(jwtToken);

                admin.setActive(false);
                admin.setLastLogout(LocalDateTime.now());
                adminRepository.save(admin);
                return "Logged out successfully";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
