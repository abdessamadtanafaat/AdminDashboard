package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.repository.AdminRepository;
import com.majorMedia.BackOfficeDashboard.security.manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.service.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController
{
    private final IAdminService IAdminService;
    private final CustomAuthenticationManager customAuthenticationManager;

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

    @PostMapping("/api/v1/auth/register")

    public ResponseEntity<HttpStatus> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        adminService.register(registerRequest);
        return new  ResponseEntity<>(HttpStatus.CREATED);
    }*/
    @GetMapping("/password-request")
    public ResponseEntity<String> passwordRequest(@RequestParam("email") String email) {

        return ResponseEntity.ok("Token: " + IAdminService.forgotPassword(email));
    }
    @GetMapping("/is-token-valid")
    public ResponseEntity<String> isTokenValid (@Param(value="token") String token)
    {
        return new ResponseEntity<>(IAdminService.checkValidity(token) , HttpStatus.ACCEPTED);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(IAdminService.resetPassword(request.getPassword(), request.getToken()), HttpStatus.ACCEPTED);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody AuthenticationRequest authentication){
        return new ResponseEntity<>(customAuthenticationManager.logout(authentication.getEmail()), HttpStatus.OK);
    }

}
