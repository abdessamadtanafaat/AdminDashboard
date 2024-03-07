package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.Exception.InvalidCredentialsException;
import com.majorMedia.BackOfficeDashboard.Exception.UserNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.AuthenticationRequest;
import com.majorMedia.BackOfficeDashboard.model.AuthenticationResponse;
import com.majorMedia.BackOfficeDashboard.model.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

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
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
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
            AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);

        }catch(UserNotFoundException | InvalidCredentialsException e ) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
            }


}
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        try {
            authenticationService.refreshToken(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
