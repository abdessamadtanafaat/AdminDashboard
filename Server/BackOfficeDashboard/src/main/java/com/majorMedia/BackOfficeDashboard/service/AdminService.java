package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.Admin;
import com.majorMedia.BackOfficeDashboard.entity.Role;
import com.majorMedia.BackOfficeDashboard.model.RegisterRequest;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Set;

public interface AdminService {

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
}
