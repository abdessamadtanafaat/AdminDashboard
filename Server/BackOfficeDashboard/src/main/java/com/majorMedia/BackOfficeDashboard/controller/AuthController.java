package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.model.requests.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateAccountRequest;
import com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.service.adminService.IAdminService;
import com.majorMedia.BackOfficeDashboard.service.authService.IAuthService;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@SecurityRequirement(name = "bearerAuth")

public class AuthController
{
    private final EmailUtils emailUtils;
    private final CustomAuthenticationManager authenticationManager;
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/password-request")
    public ResponseEntity<String> passwordRequest(@RequestParam("email") String email) {

        return ResponseEntity.ok("Token: " + authService.forgotPassword(email));
    }
    @GetMapping("/is-token-valid")
    public ResponseEntity<String> isTokenValid (@Param(value="token") String token) {
        return new ResponseEntity<>(emailUtils.checkValidity(token) , HttpStatus.ACCEPTED);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(authService.resetPassword(request), HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody AuthenticationRequest authenticationRequest , HttpServletRequest request){
        return new ResponseEntity<>(authenticationManager.logout(authenticationRequest.getEmail(), request.getHeader("Authorization").substring(7)), HttpStatus.OK);
    }
}
