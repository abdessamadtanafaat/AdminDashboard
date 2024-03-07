package com.majorMedia.BackOfficeDashboard.authentification_module.service;


import ch.qos.logback.core.model.Model;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;

public interface AdminService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String forgotPassword(String email);
    String generateToken();
    LocalDateTime expireTimeRange();
    boolean isExpiredTokenEmail(String token);
    boolean isExpiredTokenWeb(String token);
    ResponseEntity<?> checkValidity(String token);

    Admin findByEmail(String name);
}
