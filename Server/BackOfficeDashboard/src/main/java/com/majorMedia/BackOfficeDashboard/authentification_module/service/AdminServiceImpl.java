package com.majorMedia.BackOfficeDashboard.authentification_module.service;

import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidPasswordException;
import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidTokenException;
import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.EmailServiceException;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Role;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.majorMedia.BackOfficeDashboard.authentification_module.Security.SecurityConstants.TOKEN_EXPIRATION_EMAIL;

//import static com.majorMedia.BackOfficeDashboard.authentification_module.Security.SecurityConstants.TOKEN_EXPIRATION_EMAIL;

@Service
@AllArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;



    public Admin register(RegisterRequest registerRequest){
        /*var admin = Admin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
=======
        Admin admin = Admin.builder()
                .firstname(requestAdmin.getFirstname())
                .lastname(requestAdmin.getLastname())
                .email(requestAdmin.getEmail())
                .password(passwordEncoder.encode(requestAdmin.getPassword()))
>>>>>>> 3d7903f860122ff47cc460fb1ce3fac2ff5a55a9
                .role(Role.ADMIN)
                .build();
        adminRepository.save(admin);


        String jwtToken = jwtService.generateToken(admin);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .id(admin.getId())
                .email(admin.getEmail())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())

                .build();*/
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
        return adminRepository.save(admin) ;}




//    public ResponseEntity<?> authenticate(AuthenticationRequest request){
//
//        try{
//        Admin admin = adminRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new InvalidEmailException("Email is not found."));
//
//
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),
//                            request.getPassword()
//                    )
//            );
//
//        String jwtToken = jwtService.generateToken(admin);
//        admin.setTokenWeb(jwtToken);
//        admin.setActive(true);
//
//            AuthenticationResponse response = AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .id(admin.getId())
//                .email(admin.getEmail())
//                .firstname(admin.getFirstname())
//                .lastname(admin.getLastname())
//                .build();
//
//            return ResponseEntity.ok(response);
//        }catch (BadCredentialsException e) {
//            e.printStackTrace();
//            throw new InvalidPasswordException("Password is incorrect.");
//        }
//    }

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
            e.printStackTrace(); //indiro logs men be3d blast hadi
            throw new EmailServiceException("Failed to send password reset email. Please try again later.");
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
        return LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_EMAIL);
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

        try {
        //session.setAttribute("token", token);
        Optional<Admin> admin = adminRepository.findByTokenEmail(token);

        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException("Invalid Token");
        } else if (admin.isPresent() && admin.get().isUsedTokenEmail()) {
            throw new InvalidTokenException("The token is already used");
        }
        else if (!isExpiredTokenEmail(token))
        {
            throw new InvalidTokenException("The token is Expired");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body("The token is valid");
        }
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

    @Override
    public ResponseEntity<String> resetPassword(String password, String token) {

        try {
        validateToken(token);

        Optional<Admin> admin = adminRepository.findByTokenEmail(token);
        if (admin.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        admin.get().setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin.get());

        admin.get().setUsedTokenEmail(true);
        adminRepository.save(admin.get());

        return ResponseEntity.ok("Password successfully reset.");
        } catch (InvalidTokenException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    private void validateToken(String token){
        if (token == null || token.isEmpty()) {
            throw new InvalidTokenException("The token is necessary.");
        }

    }

}