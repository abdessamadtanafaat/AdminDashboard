package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.*;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.superAdminService.ISuperAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
@RequestMapping("/super-admin")
public class SuperAdminController {
    private final ISuperAdminService superAdminService;

    @GetMapping(value = "/admins")
    public ResponseEntity<List<UserResponse>> getAllAdmins(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey
            )
    {
        List<UserResponse> userResponses = superAdminService.getAllAdmins(sortBy,searchKey);
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody CreateAdminRequest admin)
    {
        Admin createdAdmin = superAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @GetMapping("/admin-details")
    public ResponseEntity<UserResponse> getAdminDetails(
            @RequestParam(value = "adminId")Long adminId){
        UserResponse superResponse = superAdminService.getAdminDetails(adminId);
        return ResponseEntity.ok(superResponse);
    }

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(
    @RequestParam(value = "nameRole")String nameRole,
    @RequestParam(value = "descriptionRole")String descriptionRole)
    {
        String roleMessage = superAdminService.addRole(nameRole, descriptionRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleMessage);
    }
    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestParam(value = "adminId")Long adminId,
                                            @RequestParam(value = "roleId")Long roleId)
    {
        String roleMessage = superAdminService.assignRoleToAdmin(adminId,roleId);
        return ResponseEntity.status(HttpStatus.OK).body(roleMessage);
    }

    @PostMapping("/create-privilege")
    public ResponseEntity<String> createPrivilege(
            @RequestParam(value = "namePrivilege")String namePrivilege,
            @RequestParam(value = "descriptionPrivilege")String descriptionPrivilege
    )
    {
        String privilegeMessage = superAdminService.addPrivilege(namePrivilege, descriptionPrivilege);
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeMessage);
    }

    @PostMapping("/assign-privilege-to-role")
    public ResponseEntity<String> assignPrivilegeToRole(
            @RequestParam(value = "roleId")Long roleId,
            @RequestParam(value = "privilegeIds")Collection<Long> privilegeIds)
            {

                String privilegeMessage = superAdminService.assignPrivilegesToRole(roleId, privilegeIds);
                return ResponseEntity.status(HttpStatus.OK).body(privilegeMessage);
            }

    @PatchMapping("/deactivateAccount")
    public ResponseEntity<String> deactivateAccount(
            @RequestParam(value = "adminId")Long adminId
                                                                )
    {
        String string = superAdminService.deactivateAccount(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(string);
    }
    @PatchMapping("/activateAccount")
    public ResponseEntity<String> activateAccount(
            @RequestParam(value = "adminId")Long adminId)
    {
        String string = superAdminService.activateAccount(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(string);

    }


}
