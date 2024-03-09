package com.majorMedia.BackOfficeDashboard.authentification_module.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.majorMedia.BackOfficeDashboard.authentification_module.Security.Manager.CustomAuthenticationManager;
import com.majorMedia.BackOfficeDashboard.authentification_module.Security.SecurityConstants;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.authentification_module.model.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.authentification_module.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@AllArgsConstructor
public class AdminController
{
    private final AdminService adminService;


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
        return new  ResponseEntity<>( HttpStatus.CREATED);
    }

//    @Operation(
//            summary = "Login endpoint",
//            description = "Endpoint to authenticate users and generate JWT token.",
//            responses = {
//                    @ApiResponse(
//                            description = "Successful login",
//                            responseCode = "200",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = ResponseEntity.class)
//                            )
//                    ),
//                    @ApiResponse(
//                            description = "Unauthorized / Invalid credentials",
//                            responseCode = "401"
//                    )
//            }
//    )

    @GetMapping("/api/v1/auth/password-request")
    public ResponseEntity<String> passwordRequest(@RequestParam("email") String email) {

        return ResponseEntity.ok("Token: " + adminService.forgotPassword(email));
    }
    @GetMapping("/api/v1/auth/is-token-valid")
    public ResponseEntity<String> isTokenValid (@Param(value="token") String token)
    {
        return new ResponseEntity<>(adminService.checkValidity(token) , HttpStatus.ACCEPTED);
    }
    @PostMapping("/api/v1/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody String password, @RequestBody String token)
    {
        return new ResponseEntity<>(adminService.resetPassword(password,token) , HttpStatus.ACCEPTED);
    }

}
