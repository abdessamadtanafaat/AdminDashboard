package com.majorMedia.BackOfficeDashboard.service.authService;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.exception.EmailServiceException;
import com.majorMedia.BackOfficeDashboard.exception.InvalidTokenException;
import com.majorMedia.BackOfficeDashboard.exception.NotFoundEmailException;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import static com.majorMedia.BackOfficeDashboard.security.SecurityConstants.RESET_PASSWORD_URL;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtils emailUtils;

    @Override
    public Admin register(RegisterRequest registerRequest) {

        boolean adminExists = adminRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if (adminExists) {
            //throw new InvalidEmailException(registerRequest.getEmail() + " Already Exists");
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Admin admin = Admin.builder().email(registerRequest.getEmail()).
                lastname(registerRequest.getLastname())
                .firstname(registerRequest.getFirstname()).
                build();

        admin.setPassword(encodedPassword);
        return adminRepository.save(admin);
    }
    @Override
    public String forgotPassword(String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundEmailException(email));

        admin.setExpiredTimeEmail(EmailUtils.expireTimeRange());
        admin.setTokenEmail(EmailUtils.generateToken());
        admin.setUsedTokenEmail(false);

        adminRepository.save(admin);

        String emailLink = RESET_PASSWORD_URL + admin.getTokenEmail();
        try {
            emailUtils.sendEmail(admin.getEmail(), emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            throw new EmailServiceException();
        }
        return emailLink;
    }
    @Override
    public String resetPassword(ResetPasswordRequest request) {

        emailUtils.checkValidity(request.getToken());

        Admin admin = adminRepository.findByTokenEmail(request.getToken())
                .orElseThrow(InvalidTokenException::new);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        adminRepository.save(admin);
        admin.setUsedTokenEmail(true);
        adminRepository.save(admin);
        return "Password successfully reset";
    }

}
