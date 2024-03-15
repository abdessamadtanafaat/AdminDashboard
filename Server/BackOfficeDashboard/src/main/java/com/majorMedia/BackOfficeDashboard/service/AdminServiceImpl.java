package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.exception.*;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RoleRequest;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.*;

@Service
@AllArgsConstructor

public class AdminServiceImpl implements IAdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final RoleRepository roleRepository;
    private final SecureRandom secureRandom = new SecureRandom();

/*    public Admin register(RegisterRequest registerRequest) {

        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if (adminExists) {
            throw new InvalidEmailException(registerRequest.getEmail() + " Already Exists");
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                .firstname(registerRequest.getFirstname()).
                build();

        admin.setPassword(encodedPassword);
        return adminRepository.save(admin)
                ;
    }*/

    public String forgotPassword(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setExpiredTimeEmail(expireTimeRange());
        admin.setTokenEmail(generateToken());
        admin.setUsedTokenEmail(false);

        adminRepository.save(admin);

        String emailLink = RESET_PASSWORD_URL + admin.getTokenEmail();
        try {
            sendEmail(admin.getEmail(), emailLink);

        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new EmailServiceException();
        }
        return emailLink;
    }

    public void sendEmail(String to, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                + "Click the link below to reset your password:<br>"
                + "<a href=\"" + emailLink + "\">Reset Password</a>"
                + "<br><br>"
                + "Ignore this email if you did not request a password reset.";

        helper.setText(emailContent, true);
        helper.setFrom("tvta3ichannel@gmail.com", "SmtpSatisnap");
        helper.setSubject("Password Reset Link");
        helper.setTo(to);

        javaMailSender.send(message);
    }

    public String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    public LocalDateTime expireTimeRange() {
        return LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_EMAIL);
    }

    public boolean isExpiredTokenEmail(String token) {
        //Admin admin = findByTokenEmail(token);
        Admin admin = adminRepository.findByTokenEmail(token)
                .orElseThrow(InvalidTokenException::new);

        return LocalDateTime.now().isAfter(admin.getExpiredTimeEmail());
    }

    public String checkValidity(String token) {

        Admin admin = adminRepository.findByTokenEmail(token)
                .orElseThrow(InvalidTokenException::new);

        if (isExpiredTokenEmail(token)) {
            throw new InvalidTokenException();
        }
        if (admin.isUsedTokenEmail()) {
            throw new InvalidTokenException();
        }
        return "The token is valid";

    }

/*    public static Admin unwarappeAdmin(Optional<Admin> entity, String message) {
        if (entity.isPresent()) return entity.get();
        else throw new NotFoundEmailException(message);
    }

    public Admin findByTokenEmail(String token) {
        Optional<Admin> entity = adminRepository.findByTokenEmail(token);

        return unwarappeAdmin(entity, "Access Denied");
    }

        public Admin findByEmail(String email) {
        Optional<Admin> entity = adminRepository.findByEmail(email);
        return unwarappeAdmin(entity, "Admin Account not found");
    }*/
    public String resetPassword(String password, String token) {

        checkValidity(token);

/*        Optional<Admin> entity = adminRepository.findByTokenEmail(token);
        Admin admin = unwarappeAdmin(entity, "Invalid Token");*/

        Admin admin = adminRepository.findByTokenEmail(token)
                .orElseThrow(InvalidTokenException::new);
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);
        admin.setUsedTokenEmail(true);
        adminRepository.save(admin);
        return "Password successfully reset";
    }

    @Transactional
    public Admin createAdmin(RegisterRequest registerRequest) {
    boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
    if (adminExists) {
        throw new AlreadyExistEmailException(registerRequest.getEmail());
    }
    String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
    Admin admin = Admin.builder().email(registerRequest.getEmail()).
            lastname(registerRequest.getLastname())
            .firstname(registerRequest.getFirstname()).
            build();

    admin.setPassword(encodedPassword);

    Collection<Role> roles = new ArrayList<>();
    for (RoleRequest roleRequest : registerRequest.getRoles()) {
        Role role = roleRepository.findByName(roleRequest.getName());
        if (role == null) {
            throw new InvalidRoleException(roleRequest.getName());
        }
        roles.add(role);
    }
    admin.setRoles(roles);

    return adminRepository.save(admin);

    }

/*    public String logout(String email) {
        try{
         Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

            admin.setActive(false);
            admin.setLastLogout(LocalDateTime.now());
            adminRepository.save(admin);
            return "Logged out successfully";

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/
    @Override
    public void hasSuperAdminRole(Authentication authentication) {
        String username = authentication.getName();

        Admin admin = adminRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean hasSuperAdminRole = admin.getRoles().stream()
                .anyMatch(role -> role.getName().equals("SUPER_ADMIN"));

        if (!hasSuperAdminRole) {
            throw new AccessDeniedException(admin.getEmail());
        }
    }





}
