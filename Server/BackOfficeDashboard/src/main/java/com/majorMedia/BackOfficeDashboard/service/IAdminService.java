package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public interface IAdminService {

    //public Admin register(RegisterRequest registerRequest);
    public String forgotPassword(String email);
    public String generateToken();
    public LocalDateTime expireTimeRange();
    public boolean isExpiredTokenEmail(String token);
    public void sendEmail(String to, String emailLink) throws MessagingException, UnsupportedEncodingException;
    public String checkValidity(String token);
    //public Admin findByEmail(String name);
    public String resetPassword(String password, String token);
    public Admin createAdmin(RegisterRequest request);
    //public String logout(String email);
    public void hasSuperAdminRole(Authentication authentication);
}