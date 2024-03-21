package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateAccountRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public interface IAdminService {

    public Admin register(RegisterRequest registerRequest);
    public String forgotPassword(String email);
    public String resetPassword(ResetPasswordRequest request);
    public String changePassword(ResetPasswordRequest resetPasswordRequest);
    public Admin createAdmin(RegisterRequest request);
    public String updateAccountSettings(UpdateAccountRequest request);
    public String uploadAdminAvatar(Integer adminId, MultipartFile file) throws IOException;
    public AdminResponse getAdminDetails(Integer adminId);
    public byte[] getImageData(Integer adminId);



    }
