package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.Exception.InvalidTokenException;
import com.majorMedia.BackOfficeDashboard.Exception.EmailServiceException;
import com.majorMedia.BackOfficeDashboard.entity.Admin;
import com.majorMedia.BackOfficeDashboard.entity.Role;
import com.majorMedia.BackOfficeDashboard.model.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.majorMedia.BackOfficeDashboard.Security.SecurityConstants.TOKEN_EXPIRATION_EMAIL;
import static com.majorMedia.BackOfficeDashboard.Security.SecurityConstants.TOKEN_LENGTH;

@Service
@AllArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final SecureRandom secureRandom = new SecureRandom();


    public Admin register(RegisterRequest registerRequest){

        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if(adminExists){
            throw new InvalidEmailException( registerRequest.getEmail() +" Already Exists");
        }
        String encodedPassword =passwordEncoder.encode(registerRequest.getPassword()) ;
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                        .firstname(registerRequest.getFirstname()).
                build();
        
        admin.setPassword(encodedPassword);
        return adminRepository.save(admin)
                ;}

    public String forgotPassword (String email){
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("Email is not found."));

        admin.setExpiredTimeEmail(expireTimeRange());
        admin.setTokenEmail(generateToken());
        admin.setUsedTokenEmail(false);

        adminRepository.save(admin);

        String emailLink = "http://localhost:3000/reset-password?token=" + admin.getTokenEmail();
        try {
            sendEmail(admin.getEmail(), emailLink);

        }catch (UnsupportedEncodingException | MessagingException e){
            throw new EmailServiceException("Failed to send password reset email. Please try again later.");
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
/*    public String generateToken(){
        return UUID.randomUUID().toString();
    }*/
    public String generateToken() {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }


    public LocalDateTime expireTimeRange(){
        return LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_EMAIL);
    }
    public boolean isExpiredTokenEmail (String token)
    {
        Admin admin = findByTokenEmail(token);
        return LocalDateTime.now().isAfter(admin.getExpiredTimeEmail());
    }

    public String checkValidity(String token) {

        Optional<Admin> entity = adminRepository.findByTokenEmail(token);
        Admin admin = unwarappeAdmin(entity,"No Admin with that token");

        if (isExpiredTokenEmail(token)){
                throw new InvalidTokenException("The token have Expired");
        }
        if (admin.isUsedTokenEmail()) {
            throw new InvalidTokenException("The token is already used");
        }

        return "The token is valid";

    }

    public static Admin unwarappeAdmin(Optional<Admin> entity, String message){
        if(entity.isPresent())return entity.get();
        else throw new InvalidEmailException(message);
    }

    public  Admin findByTokenEmail(String token){
        Optional<Admin> entity = adminRepository.findByTokenEmail(token);

        return unwarappeAdmin(entity, "Access Denied");
    }

    public  Admin findByEmail(String email){
        Optional<Admin> entity = adminRepository.findByEmail(email);
        return unwarappeAdmin(entity, "Admin Account not found");
    }

    public String resetPassword(String password, String token) {

        checkValidity(token);
        Optional<Admin> entity = adminRepository.findByTokenEmail(token);
        Admin admin = unwarappeAdmin(entity,"Invalid Token");
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);
        admin.setUsedTokenEmail(true);
        adminRepository.save(admin);
        return "Password successfully reset";
    }

    public Admin createAdmin(RegisterRequest registerRequest, Set<Role> roles) {
        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if(adminExists){
            throw new InvalidEmailException( registerRequest.getEmail() +" Already Exists");
        }
        String encodedPassword =passwordEncoder.encode(registerRequest.getPassword()) ;
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                .firstname(registerRequest.getFirstname()).
                build();
        admin.setRoles(roles);
        admin.setPassword(encodedPassword);
        return adminRepository.save(admin)
                ;}


}
