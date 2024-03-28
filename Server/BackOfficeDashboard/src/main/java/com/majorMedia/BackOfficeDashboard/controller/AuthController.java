package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.exception.NotFoundEmailException;
import com.majorMedia.BackOfficeDashboard.model.requests.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateAccountRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.security.filter.AuthenticationFilter;
import com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.service.IAdminService;
import com.majorMedia.BackOfficeDashboard.util.EmailUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")

//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
//@SecurityRequirement(name = "bearerAuth")

public class AuthController
{
    private final IAdminService IAdminService;
    private final EmailUtils emailUtils;
    private final CustomAuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        IAdminService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/password-request")
    public ResponseEntity<String> passwordRequest(@RequestParam("email") String email) {

        return ResponseEntity.ok("Token: " + IAdminService.forgotPassword(email));
    }
    @GetMapping("/is-token-valid")
    public ResponseEntity<String> isTokenValid (@Param(value="token") String token) {
        return new ResponseEntity<>(emailUtils.checkValidity(token) , HttpStatus.ACCEPTED);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(IAdminService.resetPassword(request), HttpStatus.ACCEPTED);
    }
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(IAdminService.changePassword(request), HttpStatus.ACCEPTED);
    }
    @PatchMapping("/change-account-settings")
    public ResponseEntity<String> changeAccountSettings(@RequestBody UpdateAccountRequest request){
        return new ResponseEntity<>(IAdminService.updateAccountSettings(request),HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody AuthenticationRequest authentication){
        return new ResponseEntity<>(authenticationManager.logout(authentication.getEmail(), authentication.getJwtToken()), HttpStatus.OK);
    }


}
