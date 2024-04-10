package com.majorMedia.BackOfficeDashboard.security.controller;

import com.majorMedia.BackOfficeDashboard.model.requests.AuthRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@SecurityRequirement(name = "bearerAuth")

public class AuthController
{
    private final EmailUtils emailUtils;
    private final CustomAuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid
            @RequestBody RegisterRequest registerRequest
    ) {
        String responseMessage = authenticationManager.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/password-request")
    public ResponseEntity<String> passwordRequest(@RequestParam("email") String email) {

        return ResponseEntity.ok("Token: " + authenticationManager.forgotPassword(email));
    }
    @GetMapping("/is-token-valid")
    public ResponseEntity<String> isTokenValid (@Param(value="token") String token) {
        return new ResponseEntity<>(emailUtils.checkValidity(token) , HttpStatus.ACCEPTED);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(authenticationManager.resetPassword(request), HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody AuthRequest authRequest, HttpServletRequest request){
        return new ResponseEntity<>(authenticationManager.logout(authRequest.getEmail(), request.getHeader("Authorization").substring(7)), HttpStatus.OK);
    }
}
