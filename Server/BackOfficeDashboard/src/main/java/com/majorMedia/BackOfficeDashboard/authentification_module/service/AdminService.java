package com.majorMedia.BackOfficeDashboard.authentification_module.service;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.RegisterRequest;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public interface AdminService {

    public Admin register(RegisterRequest registerRequest);
    public String forgotPassword(String email);
    public String generateToken();
    public LocalDateTime expireTimeRange();
    public boolean isExpiredTokenEmail(String token);
    public void sendEmail(String to, String emailLink) throws MessagingException, UnsupportedEncodingException;
    public String checkValidity(String token);
    public Admin findByEmail(String name);
    public String resetPassword(String password, String token);
}
