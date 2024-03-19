package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.exception.*;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RoleRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateAccountRequest;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
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

    public String resetPassword(String password, String token) {

        checkValidity(token);

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

    @Override
    public String changePassword(String password, String jwtToken) {

        if(!isValidJwt(jwtToken)){
            throw new InvalidTokenException();
        }

        String email = extractEmailFromToken(jwtToken);

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setPassword(passwordEncoder.encode(password));
        admin.setActive(false);
        adminRepository.save(admin);
        return "Password changed successfully";
    }

   //private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Override
    public String updateAccountSettings(UpdateAccountRequest request){


        if(!isValidJwt(request.getJwtToken())){
            throw new InvalidTokenException();
        }
        String email = extractEmailFromToken(request.getJwtToken());

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setFirstname(request.getFirstname());
        admin.setLastname(request.getLastname());
        admin.setEmail(request.getEmail());

        adminRepository.save(admin);

        return "Account settings updated successfully";
    }

    @Override
    public String saveAdminAvatar(Integer adminId, MultipartFile file) throws IOException {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()->new RuntimeException("Admin not found"));

        makeDirectoryIfNotExist(SecurityConstants.AVATAR_DIRECTORY);

        String fileName = adminId + "-avatar" + FilenameUtils.getExtension(file.getOriginalFilename());
        Path filePath = Paths.get(AVATAR_DIRECTORY, fileName);

        Files.write(filePath, file.getBytes());

        String avatarUrl = "/admin/avatars" + fileName;
        admin.setAvatarUrl(avatarUrl);
        adminRepository.save(admin);

        return avatarUrl;

    }

    private void makeDirectoryIfNotExist(String directoryPath) {
        File directory = new File(directoryPath);
        if(!directory.exists()){
            directory.mkdir();
        }
    }

    public String extractEmailFromToken(String jwtToken) {
        try {
            // Parse the JWT token
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwtToken);

            // Extract email claim from the token
            return claims.getBody().get("email", String.class);
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            return null; // Or handle the exception as needed
        }
    }

    private boolean isValidJwt(String jwtToken) {
        try{


        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        long currentTimeMillis = System.currentTimeMillis();
        return  !claims.getExpiration().before(new Date(currentTimeMillis));

            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e ){

            e.printStackTrace();
            return false;
        }
    }

}
