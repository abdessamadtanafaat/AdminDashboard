package com.majorMedia.BackOfficeDashboard.authentification_module.controller;

import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.authentification_module.service.UserService;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.User;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.ForgotPasswordToken;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.ForgotPasswordRepository;
import com.majorMedia.BackOfficeDashboard.authentification_module.service.ForgotPasswordService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/password-request")
    public ResponseEntity<?> savePasswordRequest(@RequestParam("email") String email) {

        try {
            String token = forgotPasswordService.forgotPassword(email);
            return ResponseEntity.ok("Token: " + token);
        } catch (InvalidEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword (@Param(value="token") String token, Model model, HttpSession session)
    {
        session.setAttribute("token", token);
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(token);
        return forgotPasswordService.chekValidity(forgotPasswordToken);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> saveResetPassword(@RequestParam("password") String password,
                                                    @RequestParam("token") String token) {
        // Validate the token (ensure it's not null or empty)
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or missing token.");
        }

        // Retrieve the forgot password token from the repository
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(token);
        if (forgotPasswordToken == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        // Retrieve the user associated with the token
        User user = forgotPasswordToken.getUser();
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        // Update the user's password
        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);

        // Mark the token as used
        forgotPasswordToken.setUsed(true);
        forgotPasswordRepository.save(forgotPasswordToken);

        // Return a success message
        return ResponseEntity.ok("Password successfully reset.");
    }

}
