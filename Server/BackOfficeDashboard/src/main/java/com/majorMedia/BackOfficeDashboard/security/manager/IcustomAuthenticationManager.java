package com.majorMedia.BackOfficeDashboard.security.manager;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;

public interface IcustomAuthenticationManager {
    public String register(RegisterRequest registerRequest);
    public String forgotPassword(String email);
    public String resetPassword(ResetPasswordRequest request);
    public String logout(String email, String jwtToken);
}
