package com.majorMedia.BackOfficeDashboard.authentification_module.service;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.RegisterRequest;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface AdminService {

    public Admin register(RegisterRequest registerRequest);
    public String forgotPassword(String email);
    public String generateToken();
    public LocalDateTime expireTimeRange();
    public boolean isExpiredTokenEmail(String token);
    boolean isExpiredTokenWeb(String token);
    public String checkValidity(String token);
    public Admin findByEmail(String name);

    public String resetPassword(String password, String token);
}
