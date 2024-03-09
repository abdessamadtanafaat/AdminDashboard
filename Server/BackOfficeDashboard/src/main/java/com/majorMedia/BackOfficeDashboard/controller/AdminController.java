package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.model.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
/*    @PostMapping("/api/v1/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody String password, String token)
    {
        return new ResponseEntity<>(adminService.resetPassword(password,token) , HttpStatus.ACCEPTED);
    }*/
    @PostMapping("/api/v1/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        String token = requestBody.get("token");
        return new ResponseEntity<>(adminService.resetPassword(password, token), HttpStatus.ACCEPTED);
    }
}
