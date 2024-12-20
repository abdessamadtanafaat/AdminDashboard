package com.majorMedia.BackOfficeDashboard.service.adminService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.BusinessResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.ObjectsList;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;
import org.hibernate.query.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface IAdminService {
    public String verifyToken(String token);

    public String updateAccountSettings(MultipartFile file, String firstname, String lastname, String email) throws IOException;

    public Admin uploadAdminAvatar(Long adminId, MultipartFile file) throws IOException;

    public byte[] getImageData(Long adminId);

    public String changePassword(ResetPasswordRequest resetPasswordRequest);

    public String resetPassword(String currentPassword,String email , String password);
//
//    public ObjectsList<Business> getAllBusiness(String searchKey, String sortOrder, int page);
//
//    ObjectsList<Campaign> getAllCampagnes(String searchKey, String sortOrder, int page);

}
