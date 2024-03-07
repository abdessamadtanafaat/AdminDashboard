/*
package com.majorMedia.BackOfficeDashboard.authentification_module.controller;

import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.authentification_module.service.AdminService;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@AllArgsConstructor
public class ForgotPasswordController {

    private final  AdminService adminService;
   private final  AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/password-request")
    public ResponseEntity<?> savePasswordRequest(@RequestParam("email") String email) {

        try {
            String token = adminService.forgotPassword(email);
            return ResponseEntity.ok("Token: " + token);
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword (@Param(value="token") String token, HttpSession session)
    {
        session.setAttribute("token", token);
        Admin admin = adminRepository.findByTokenEmail(token);
        return adminService.chekValidity(forgotPasswordToken);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> saveResetPassword(@RequestBody String password,
                                                    @RequestBody String token) {
        // Validate the token (ensure it's not null or empty)
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or missing token.");
        }

        // Retrieve the forgot password token from the repository
        Admin admin = adminRepository.findByToken(token);
        if (forgotPasswordToken == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        // Retrieve the user associated with the token
        Admin admin = forgotPasswordToken.getUser();
        if (admin == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        // Update the user's password
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);

        // Mark the token as used
        forgotPasswordToken.setUsed(true);
        adminRepository.save(forgotPasswordToken);

        // Return a success message
        return ResponseEntity.ok("Password successfully reset.");
    }

}
*/
