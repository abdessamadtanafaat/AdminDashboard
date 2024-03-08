package com.majorMedia.BackOfficeDashboard.authentification_module.controller;

import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidEmailException;
import com.majorMedia.BackOfficeDashboard.authentification_module.Exception.InvalidPasswordException;
import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
        return new  ResponseEntity<>(  HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login endpoint",
            description = "Endpoint to authenticate users and generate JWT token.",
            responses = {
                    @ApiResponse(
                            description = "Successful login",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid credentials",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        try{
            AuthenticationResponse response = adminService.authenticate(request);
            return ResponseEntity.ok(response);

        }catch(InvalidPasswordException | InvalidEmailException e ) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
            // e.printStackTrace();
            return new ResponseEntity<>("Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
