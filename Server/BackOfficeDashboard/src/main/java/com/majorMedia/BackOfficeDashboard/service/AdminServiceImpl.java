package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.exception.*;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RoleRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateAccountRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.repository.RoleRepository;
import com.majorMedia.BackOfficeDashboard.security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import com.majorMedia.BackOfficeDashboard.util.ImageUtils;
import com.majorMedia.BackOfficeDashboard.util.JwtUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.*;

@Service
@AllArgsConstructor

public class AdminServiceImpl implements IAdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailUtils emailUtils;

    @Override
    public Admin register(RegisterRequest registerRequest) {

        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if (adminExists) {
            //throw new InvalidEmailException(registerRequest.getEmail() + " Already Exists");
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                .firstname(registerRequest.getFirstname()).
                build();

        admin.setPassword(encodedPassword);
        return adminRepository.save(admin);
    }
    @Override
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
    public String changePassword(ResetPasswordRequest resetPasswordRequest) {

        if (!JwtUtils.isValidJwt(resetPasswordRequest.getToken())) {
            throw new InvalidTokenException();
        }

        String email = JwtUtils.extractEmailFromToken(resetPasswordRequest.getToken());

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        admin.setActive(false);
        adminRepository.save(admin);
        return "Password changed successfully";
    }
    @Override
    public String updateAccountSettings(UpdateAccountRequest request) {


        if (!JwtUtils.isValidJwt(request.getJwtToken())) {
            throw new InvalidTokenException();
        }
        String email = JwtUtils.extractEmailFromToken(request.getJwtToken());

        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setFirstname(request.getFirstname());
        admin.setLastname(request.getLastname());
        admin.setEmail(request.getEmail());

        adminRepository.save(admin);

        return "Account settings updated successfully";
    }
    @Override
    public String uploadAdminAvatar(Integer adminId, MultipartFile file) throws IOException {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(adminId, Admin.class));

        ImageUtils.makeDirectoryIfNotExist(SecurityConstants.AVATAR_DIRECTORY);

        String fileName = admin.getFirstname() +
                "_" + admin.getLastname() +
                "_avatar." +
                FilenameUtils.getExtension(file.getOriginalFilename());
        Path filePath = Paths.get(AVATAR_DIRECTORY, fileName);

        Files.write(filePath, file.getBytes());

        String avatarUrl = "/avatars/" + fileName;

        byte[] imageData = file.getBytes();
        byte[] compressedImageData = ImageUtils.compressImage(imageData);
        admin.setImageByte(compressedImageData);

        admin.setAvatarUrl(avatarUrl);
        adminRepository.save(admin);

        return "Image updated Successfully";

    }
    @Override
    public AdminResponse getAdminDetails(Integer adminId){
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(()-> new EntityNotFoundException(adminId, Admin.class));

        byte[] imageData = getImageData(adminId);

        AdminResponse adminResponse = AdminResponse.builder()
                .id(admin.getId())
                .firstname(admin.getFirstname())
                .lastname(admin.getLastname())
                .email(admin.getEmail())
                .imageData(imageData)
                .build();

        return adminResponse;
    }
    @Override
    public byte[] getImageData(Integer adminId) {

            Admin admin = adminRepository.findById(adminId)
                    .orElseThrow(() -> new EntityNotFoundException(adminId,Admin.class));

            byte[] imageData = ImageUtils.decompressImage(admin.getImageByte());

            return imageData;
        }


}
