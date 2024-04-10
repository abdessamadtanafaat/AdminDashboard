package com.majorMedia.BackOfficeDashboard.security.manager;

import com.majorMedia.BackOfficeDashboard.exception.*;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;

import com.majorMedia.BackOfficeDashboard.security.BlacklistToken.BlacklistRepository;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import com.majorMedia.BackOfficeDashboard.util.ServiceUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.RESET_PASSWORD_URL;


@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager, IcustomAuthenticationManager {

    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    private BlacklistRepository blacklistRepository;
    private ServiceUtils serviceUtils;
    private final EmailUtils emailUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Check if email or password is null or empty
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Email and password must not be null or empty");
        }
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

    @Override
    public String logout(String email, String jwtToken) {

        try {
            Optional<Admin> adminOptional = adminRepository.findByEmail(email);
            Admin admin = adminOptional.orElseThrow(() -> new NotFoundEmailException(email));

            if (!blacklistRepository.existsByToken(jwtToken)) {
                serviceUtils.addToBlacklist(jwtToken);

                admin.setActive(false);
                admin.setLastLogout(LocalDateTime.now());
                adminRepository.save(admin);
                return "Logged out successfully";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String register(RegisterRequest registerRequest) {

        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if (!adminExists) {
            throw new AlreadyExistEmailException(registerRequest.getEmail() + " Already Exists");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                .firstname(registerRequest.getFirstname()).
                build();

        admin.setPassword(encodedPassword);
        adminRepository.save(admin);
        return "Admin Registered Successfully";

    }

    public String forgotPassword(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setExpiredTimeEmail(EmailUtils.expireTimeRange());
        admin.setTokenEmail(EmailUtils.generateToken());
        admin.setUsedTokenEmail(false);

        adminRepository.save(admin);

        String emailLink = RESET_PASSWORD_URL + admin.getTokenEmail();
        try {
            emailUtils.sendEmail(admin.getEmail(), emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new EmailServiceException();
        }
        return emailLink;
    }
    @Override
    public String resetPassword(ResetPasswordRequest request) {

        emailUtils.checkValidity(request.getToken());

        Admin admin = adminRepository.findByTokenEmail(request.getToken())
                .orElseThrow(InvalidTokenException::new);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        adminRepository.save(admin);
        admin.setUsedTokenEmail(true);
        adminRepository.save(admin);
        return "Password successfully reset";
    }


}
