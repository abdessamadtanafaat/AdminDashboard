package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.User;
import com.majorMedia.BackOfficeDashboard.model.ForgotPasswordToken;
import com.majorMedia.BackOfficeDashboard.repository.ForgotPasswordRepository;
import com.majorMedia.BackOfficeDashboard.service.ForgotPasswordService;
import com.majorMedia.BackOfficeDashboard.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ForgotPasswordController {
    @Autowired
    private UserService userService ;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/password-request")
    public String savePasswordRequest(@RequestParam("email") String email, Model model)
    {
        Optional<User> userOptional  = userService.findByEmail(email);
        User user = userOptional.get();
        if (user == null)
        {
            return "This Email is not registered";
        }
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setExpreTime(forgotPasswordService.expireTimeRange());
        forgotPasswordToken.setToken(forgotPasswordService.generateToken());
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setUsed(false);

        forgotPasswordRepository.save(forgotPasswordToken);

        String emailLink = "http://localhost:8080/reset-password?token=" + forgotPasswordToken.getToken();
/*        try {
            forgotPasswordService.sendEmail(user.getUsername(), "Password Reset Link", emailLink);

        }catch (UnsupportedEncodingException | MessagingException e){
            model.addAttribute("Error", "Error While sending email");
            return emailLink;
        }*/


        //return "redirect:/password-request?success";
        //return forgotPasswordToken.getToken();
        return emailLink;
    }

    @GetMapping("/reset-password")
    public String resetPassword (@Param(value="token") String token, Model model, HttpSession session)
    {
        session.setAttribute("token", token);
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(token);
        return forgotPasswordService.chekValidity(forgotPasswordToken, model);
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
