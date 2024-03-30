package com.majorMedia.BackOfficeDashboard.service.authService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;

public interface IAuthService {
    public Admin register(RegisterRequest registerRequest);
    public String forgotPassword(String email);
    public String resetPassword(ResetPasswordRequest request);

}
