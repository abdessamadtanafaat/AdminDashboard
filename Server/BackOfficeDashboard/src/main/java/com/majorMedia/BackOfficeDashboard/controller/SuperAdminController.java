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
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
@RequestMapping("/super-admin")

public class SuperAdminController {


    private final ISuperAdminService superAdminService;

    @LogActivity
    @GetMapping(value = "/admins")
    public ResponseEntity<List<Admin>> getAllAdmins(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required=false ,name="page" , defaultValue="1") int page
            )
    {
        return ResponseEntity.ok(superAdminService.getAllAdmins(searchKey ,sortBy ,page));
    }

    @LogActivity
    @PostMapping("/create-admin")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin)
    {
        Admin createdAdmin = superAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @LogActivity
    @GetMapping("/admin-details")
    public ResponseEntity<Admin> getAdminDetails(
            @RequestParam(value = "adminId")Long adminId){
        return ResponseEntity.ok(superAdminService.getAdminDetails(adminId));
    }
    @LogActivity
    @PostMapping("/create-role")
    public ResponseEntity<Role> createRole(
    @RequestParam(value = "nameRole")String nameRole,
    @RequestParam(value = "descriptionRole")String descriptionRole)
    {
        Role role = superAdminService.addRole(nameRole, descriptionRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }
    @LogActivity
    @PostMapping("/assign-role")
    public ResponseEntity<Admin> assignRoleToAdmin(@RequestParam(value = "adminId")Long adminId,
                                            @RequestParam(value = "roleId")Long roleId)
    {
        Admin admin = superAdminService.assignRoleToAdmin(adminId,roleId);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }
    @LogActivity
    @PostMapping("/create-privilege")
    public ResponseEntity<Privilege> createPrivilege(
            @RequestParam(value = "namePrivilege")String namePrivilege,
            @RequestParam(value = "descriptionPrivilege")String descriptionPrivilege
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(superAdminService.addPrivilege(namePrivilege, descriptionPrivilege));
    }

    @LogActivity
    @PostMapping("/assign-privilege-to-role")
    public ResponseEntity<Role> assignPrivilegeToRole(
            @RequestParam(value = "roleId")Long roleId,
            @RequestParam(value = "privilegeIds")Collection<Long> privilegeIds)
            {

                return ResponseEntity.status(HttpStatus.OK).body(superAdminService.assignPrivilegesToRole(roleId, privilegeIds));
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
    public ResponseEntity<List<Role>> getAllRoles(
    )
    {
        return ResponseEntity.ok(superAdminService.getAllRoles());
    }

    @LogActivity
    @GetMapping(value = "/privileges")
    public ResponseEntity<List<Privilege>> getAllPrivileges(
    )
    {
        return ResponseEntity.ok(superAdminService.getAllPrivileges());
    }
    @LogActivity
    @PatchMapping("/revoke-privilege-from-role")
    public ResponseEntity<Role> RevokePrivilegeFromRole(
            @RequestParam(value = "roleId")Long roleId,
            @RequestParam(value = "privilegeIds")Collection<Long> privilegeIds)
    {
        return ResponseEntity.status(HttpStatus.OK).body(superAdminService.revokePrivilegesFromRole(roleId, privilegeIds));
    }
    @LogActivity
    @PatchMapping("/revoke-role")
    public ResponseEntity<Admin> revokeRole(@RequestParam(value = "adminId")Long adminId,
                                             @RequestParam(value = "roleId")Long roleId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(superAdminService.revokeRoleFromAdmin(adminId,roleId));
    }
}
