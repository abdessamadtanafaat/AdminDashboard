package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.superAdminService.ISuperAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ADMIN') ")
@RequestMapping("/super-admin")

public class SuperAdminController {

    private final ISuperAdminService superAdminService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String filterByProfile,
            @RequestParam(required = false) String filterByStatus)
    {
        List<UserResponse> userResponses = superAdminService.getAllUsers(sortBy,searchKey,filterByProfile,filterByStatus);
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody RegisterRequest admin, Authentication authentication)
    {
        Admin createdAdmin = superAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdminDetails(@PathVariable Integer adminId){
        AdminResponse adminResponse = superAdminService.getAdminDetails(adminId);
        return ResponseEntity.ok(adminResponse);
    }

}
