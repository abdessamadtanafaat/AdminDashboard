package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.service.IAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final IAdminService IAdminService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/create-admin")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Admin> createAdmin(@RequestBody RegisterRequest admin, Authentication authentication)
        {
        Admin createdAdmin = IAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @PostMapping("/{adminId}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable("adminId") Integer adminId,
                                               @RequestParam("avatar")MultipartFile file) {
        try{
            String imageUrl = IAdminService.saveAdminAvatar(adminId, file);
            return ResponseEntity.ok("imageUrl");

        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload avatar");
        }
    }


}
