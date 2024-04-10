package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.model.requests.ResetPasswordRequest;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.adminService.IAdminService;
import com.majorMedia.BackOfficeDashboard.service.superAdminService.ISuperAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
//@PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final IAdminService adminService;

    @PatchMapping("/{adminId}/avatar")
    public ResponseEntity<Admin> uploadAvatar(@PathVariable("adminId") Long adminId,
                                              @RequestParam("avatar")MultipartFile file) throws IOException {
            return new ResponseEntity<> (adminService.uploadAdminAvatar(adminId, file),HttpStatus.CREATED);

    }
    @GetMapping(value = "/image/{adminId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAdminAvatar(@PathVariable Long adminId) {
        byte[] imageData = adminService.getImageData(adminId);
        return ResponseEntity.ok().body(imageData);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordRequest request){
        return new ResponseEntity<>(adminService.changePassword(request), HttpStatus.ACCEPTED);
    }
    @PatchMapping("/change-account-settings")
    public ResponseEntity<String> changeAccountSettings(
            @RequestParam(value = "file" ,required = false) MultipartFile file,
            @RequestParam(value = "firstname") @Valid  String firstname ,
            @RequestParam(value = "lastname") @Valid  String lastname ,
            @RequestParam("email") String email ) throws IOException {
        return new ResponseEntity<>(adminService.updateAccountSettings(file , firstname , lastname , email),HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/owners")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey
    )
    {
        List<UserResponse> userResponses = adminService.getAllOwners(sortBy,searchKey);
        return ResponseEntity.ok(userResponses);
    }
    @PatchMapping("/deactivateAccount/{ownerId}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Long ownerId)
    {
        String string = adminService.deactivateAccount(ownerId);
        return ResponseEntity.ok(string);
    }

    @PatchMapping("/activateAccount/{ownerId}")
    public ResponseEntity<String> activateAccount(@PathVariable Long ownerId)
    {
        String string = adminService.activateAccount(ownerId);
        return ResponseEntity.ok(string);
    }

}
