package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.*;
import com.majorMedia.BackOfficeDashboard.model.responses.PermissionsResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.superAdminService.ISuperAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@AllArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
//@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
@RequestMapping("/super-admin")

public class SuperAdminController {


    private final ISuperAdminService superAdminService;

    @LogActivity
    @GetMapping(value = "/admins")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey
            )
    {
        List<UserResponse> userResponses = superAdminService.getAllAdmins(sortBy,searchKey);
        return ResponseEntity.ok(userResponses);
    }

    @LogActivity
    @PostMapping("/create-admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody CreateAdminRequest admin)
    {
        Admin createdAdmin = superAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @LogActivity
    @GetMapping("/admin-details")
    public ResponseEntity<UserResponse> getAdminDetails(
            @RequestParam(value = "adminId")Long adminId){
        UserResponse superResponse = superAdminService.getAdminDetails(adminId);
        return ResponseEntity.ok(superResponse);
    }
    @LogActivity
    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(
    @RequestParam(value = "nameRole")String nameRole,
    @RequestParam(value = "descriptionRole")String descriptionRole)
    {
        String roleMessage = superAdminService.addRole(nameRole, descriptionRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleMessage);
    }
    @LogActivity
    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestParam(value = "adminId")Long adminId,
                                            @RequestParam(value = "roleId")Long roleId)
    {
        String roleMessage = superAdminService.assignRoleToAdmin(adminId,roleId);
        return ResponseEntity.status(HttpStatus.OK).body(roleMessage);
    }
    @LogActivity
    @PostMapping("/create-privilege")
    public ResponseEntity<String> createPrivilege(
            @RequestParam(value = "namePrivilege")String namePrivilege,
            @RequestParam(value = "descriptionPrivilege")String descriptionPrivilege
    )
    {
        String privilegeMessage = superAdminService.addPrivilege(namePrivilege, descriptionPrivilege);
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeMessage);
    }

    @LogActivity
    @PostMapping("/assign-privilege-to-role")
    public ResponseEntity<String> assignPrivilegeToRole(
            @RequestParam(value = "roleId")Long roleId,
            @RequestParam(value = "privilegeIds")Collection<Long> privilegeIds)
            {

                String privilegeMessage = superAdminService.assignPrivilegesToRole(roleId, privilegeIds);
                return ResponseEntity.status(HttpStatus.OK).body(privilegeMessage);
            }

    @LogActivity
    @PatchMapping("/deactivateAccount")
    public ResponseEntity<String> deactivateAccount(
            @RequestParam(value = "adminId")Long adminId
                                                                )
    {
        String string = superAdminService.deactivateAccount(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(string);
    }
    @LogActivity
    @PatchMapping("/activateAccount")
    public ResponseEntity<String> activateAccount(
            @RequestParam(value = "adminId")Long adminId)
    {
        String string = superAdminService.activateAccount(adminId);
        return ResponseEntity.status(HttpStatus.OK).body(string);

    }
    @LogActivity
    @GetMapping(value = "/roles")
    public ResponseEntity<List<PermissionsResponse>> getAllRoles(
    )
    {
        List<PermissionsResponse> roleResponse = superAdminService.getAllRoles();
        return ResponseEntity.ok(roleResponse);
    }

    @LogActivity
    @GetMapping(value = "/privileges")
    public ResponseEntity<List<PermissionsResponse>> getAllPrivileges(
    )
    {
        List<PermissionsResponse> privilegesResponse = superAdminService.getAllPrivileges();
        return ResponseEntity.ok(privilegesResponse);
    }
    @LogActivity
    @DeleteMapping("/revoke-privilege-from-role")
    public ResponseEntity<String> RevokePrivilegeFromRole(
            @RequestParam(value = "roleId")Long roleId,
            @RequestParam(value = "privilegeIds")Collection<Long> privilegeIds)
    {

        String revokeMessage = superAdminService.revokePrivilegesFromRole(roleId, privilegeIds);
        return ResponseEntity.status(HttpStatus.OK).body(revokeMessage);
    }
    @LogActivity
    @DeleteMapping("/revoke-role")
    public ResponseEntity<String> revokeRole(@RequestParam(value = "adminId")Long adminId,
                                             @RequestParam(value = "roleId")Long roleId)
    {
        String revokeMessage = superAdminService.revokeRoleFromAdmin(adminId,roleId);
        return ResponseEntity.status(HttpStatus.OK).body(revokeMessage);
    }
}
