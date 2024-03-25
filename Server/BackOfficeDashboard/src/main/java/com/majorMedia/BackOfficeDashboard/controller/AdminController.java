package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.RegisterRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.AdminResponse;
import com.majorMedia.BackOfficeDashboard.service.IAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/admin")
public class AdminController {

    private final IAdminService IAdminService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody RegisterRequest admin, Authentication authentication)
        {
        Admin createdAdmin = IAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ADMIN')")
    @PatchMapping("/{adminId}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable("adminId") Integer adminId,
                                               @RequestParam("avatar")MultipartFile file) {
                                               //throws IOException
        try{
            String imageUrl = IAdminService.uploadAdminAvatar(adminId, file);
            return new ResponseEntity<> (imageUrl,HttpStatus.CREATED);

        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload avatar");
        }
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ADMIN')")
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdminDetails(@PathVariable Integer adminId){
        AdminResponse adminResponse = IAdminService.getAdminDetails(adminId);
        return ResponseEntity.ok(adminResponse);
    }

/*    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ADMIN')")
    @GetMapping("/image/{adminId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer adminId) {
        byte[] imageData = IAdminService.getImageData(adminId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }*/

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ADMIN')")
    @GetMapping(value = "/image/{adminId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAdminAvatar(@PathVariable Integer adminId) {
        byte[] imageData = IAdminService.getImageData(adminId);
        return ResponseEntity.ok().body(imageData);
    }

}
