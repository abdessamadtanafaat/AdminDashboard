package com.majorMedia.BackOfficeDashboard.service.adminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface IAdminService {

    public String updateAccountSettings(MultipartFile file , String firstname , String lastname , String email) throws IOException;
    public Admin uploadAdminAvatar(Long adminId, MultipartFile file) throws IOException;
    public byte[] getImageData(Long adminId);
    public String changePassword(ResetPasswordRequest resetPasswordRequest);
    public List<UserResponse> getAllOwners(String sortBy , String searchKey);
    public String deactivateAccount(Long ownerId);
    public String activateAccount(Long ownerId);
}
