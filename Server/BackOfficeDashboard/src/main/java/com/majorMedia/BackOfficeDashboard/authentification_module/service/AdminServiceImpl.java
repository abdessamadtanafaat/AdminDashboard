package com.majorMedia.BackOfficeDashboard.authentification_module.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidPasswordException;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Role;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    private final int MINUTES = 10;


    public Admin register(Admin admin){
        /*var admin = Admin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        var refreshToken = jwtService.generateRefreshToken(admin);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(admin.getId())
                .email(admin.getEmail())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .build();*/
        boolean adminExists = adminRepository.findByEmail(admin.getEmail()).isPresent();
        if(adminExists){
            throw new IllegalStateException("the Email" + admin.getEmail() +" Already Exists");
        }

        String encodedPassword =passwordEncoder.encode(admin.getPassword()) ;
        admin.setPassword(encodedPassword);
        return adminRepository.save(admin);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidEmailException("Email is not found."));

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        }catch (BadCredentialsException e) {
            throw new InvalidPasswordException("Password is not correct");
        }

        var jwtToken = jwtService.generateToken(admin);

        admin.setTokenWeb(jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .id(admin.getId())
                .email(admin.getEmail())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .build();
    }


    public String forgotPassword (String email){
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidEmailException("Email is not found."));

        admin.setExpreTimeEmail(expireTimeRange());
        admin.setTokenEmail(generateToken());
        admin.setUsedTokenEmail(false);

        adminRepository.save(admin);

        String emailLink = "http://localhost:3000/reset-password?token=" + admin.getTokenEmail();
        try {
            sendEmail(admin.getUsername(), "Password Reset Link", emailLink);

        }catch (UnsupportedEncodingException | MessagingException e){
            e.printStackTrace();
        }

        return emailLink;
    }

    private void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
                + "Click the link below to reset your password:<br>"
                + "<a href=\"" + emailLink + "\">Reset Password</a>"
                + "<br><br>"
                + "Ignore this email if you did not request a password reset.";

        helper.setText(emailContent, true);
        helper.setFrom("tvta3ichannel@gmail.com", "SmtpSatisnap");
        helper.setSubject(subject);
        helper.setTo(to);

        javaMailSender.send(message);
    }

    public String generateToken(){
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expireTimeRange(){
        return LocalDateTime.now().plusMinutes(MINUTES);
    }
    public boolean isExpiredTokenEmail (String token)
    {
        Admin admin = findByTokenEmail(token);
        return LocalDateTime.now().isAfter(admin.getExpreTimeEmail());
    }
    public boolean isExpiredTokenWeb(String token)
    {
        Admin admin = findByTokenWeb(token);
        return LocalDateTime.now().isAfter(admin.getExpreTimeWeb());
    }

    @Override
    public ResponseEntity<?> checkValidity(String token) {
        return null;
    }

    public ResponseEntity<?> chekValidity (String token)
    {
        Admin admin = findByTokenEmail(token);
        try{
            if(token == null || token.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");
            }
            else if(admin.isUsedTokenEmail())
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token is already used");
            }
            else{
                return ResponseEntity.ok("The token is valid");
            }

        }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public static Admin unwarappeAdmin(Optional<Admin> entity, String message){
        if(entity.isPresent())return entity.get();
        else throw new InvalidEmailException(message);
    }

    public  Admin findByTokenEmail(String token){
        Optional<Admin> entity = adminRepository.findByTokenEmail(token);
        Admin admin = unwarappeAdmin(entity, "Access Denied");

        return admin;
    }

    public  Admin findByTokenWeb(String token){
        Optional<Admin> entity = adminRepository.findByTokenWeb(token);
        Admin admin = unwarappeAdmin(entity, "Access Denied");
        return admin;
    }

    public  Admin findByEmail(String email){
        Optional<Admin> entity = adminRepository.findByEmail(email);
        Admin admin = unwarappeAdmin(entity, "Admin Account not found");
        return admin;
    }

}
