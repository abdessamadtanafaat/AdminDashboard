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
    private final CustomAuthenticationManager authenticationManager;

/*
    @Operation(
            summary = "User Registration",
            description = "Endpoint to register new users.",
            responses = {
                    @ApiResponse(
                            description = "User successfully registered",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request / User already exists",
                            responseCode = "400"
                    )
            }
    )

    @PostMapping("/register")

    public ResponseEntity<HttpStatus> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        adminService.register(registerRequest);
        return new  ResponseEntity<>(HttpStatus.CREATED);
    }*/

/*
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest loginRequest) throws ChangeSetPersister.NotFoundException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        try {
            authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password supplied");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Admin admin = adminRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        String token = (authenticationFilter.generateToken(admin));
        return ResponseEntity.ok(new AuthenticationResponse(token, admin));
    }
*/

    @GetMapping("/password-request")
    public ResponseEntity<String> passwordRequest(@RequestParam("email") String email) {

        return ResponseEntity.ok("Token: " + IAdminService.forgotPassword(email));
    }
    @GetMapping("/is-token-valid")
    public ResponseEntity<String> isTokenValid (@Param(value="token") String token) {
        return new ResponseEntity<>(IAdminService.checkValidity(token) , HttpStatus.ACCEPTED);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(IAdminService.resetPassword(request.getPassword(), request.getJwtToken()), HttpStatus.ACCEPTED);
    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(IAdminService.changePassword(request.getPassword(), request.getJwtToken()), HttpStatus.ACCEPTED);
    }
    @PutMapping("/change-account-settings")
    public ResponseEntity<String> changeAcccountSettings(@RequestBody UpdateAccountRequest request){
        return new ResponseEntity<>(IAdminService.updateAccountSettings(request),HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody AuthenticationRequest authentication){
        return new ResponseEntity<>(authenticationManager.logout(authentication.getEmail()), HttpStatus.OK);
    }

}
