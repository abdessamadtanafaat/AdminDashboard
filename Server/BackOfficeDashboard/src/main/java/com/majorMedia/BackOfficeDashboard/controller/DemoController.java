package com.majorMedia.BackOfficeDashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@SecurityRequirement(name="Bearer Token Authentication")
@Tag(name="DemoController")
public class DemoController {
    @Operation(
            description = "Post endpoint for Demo",
            summary = "This is a summary for Demo application",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200"
            ),
                    @ApiResponse(
                            description = "Unauthorized / invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
