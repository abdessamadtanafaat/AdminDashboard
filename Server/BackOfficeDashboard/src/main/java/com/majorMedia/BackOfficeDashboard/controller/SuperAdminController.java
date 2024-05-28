package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.model.requests.*;
import com.majorMedia.BackOfficeDashboard.model.responses.*;
import com.majorMedia.BackOfficeDashboard.service.superAdminService.ISuperAdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@RestController
@AllArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
@RequestMapping("/super-admin")

public class SuperAdminController {


    private final ISuperAdminService superAdminService;

    @LogActivity
    @GetMapping(value = "/admins")
    public ResponseEntity<ObjectsList<Admin>> getAllAdmins(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required=false ,name="page" , defaultValue="1") int page
            )
    {
        return ResponseEntity.ok(superAdminService.getAllAdmins(searchKey ,sortBy ,page));
    }

    @LogActivity
    @GetMapping(value = "/role-details")
    public ResponseEntity<RolePrivilegeResponse> getRoleDetails(
            @RequestParam(value = "roleId")Long roleId
    )
    {
        return ResponseEntity.ok(superAdminService.getRoleDetails(roleId));
    }

    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) throws MessagingException, UnsupportedEncodingException {
        Admin createdAdmin = superAdminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @LogActivity
    @PatchMapping("/update-admin")
    public ResponseEntity<Admin> updateAdmin(@Valid @RequestBody AdminRolesRequest admin)
    {
        Admin createdAdmin = superAdminService.updateAdmin(admin.getAdminId(), admin.getRoles());
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }
    @LogActivity
    @GetMapping("/admin-details")
    public ResponseEntity<AdminRolesResponse> getAdminDetails(
            @RequestParam(value = "adminId")Long adminId){
           return ResponseEntity.ok(superAdminService.getAdminDetails(adminId));
    }
    @LogActivity
    @PostMapping("/create-role")
    public ResponseEntity<Role> createRole(
    @Valid @RequestBody Role role)
    {

        return ResponseEntity.status(HttpStatus.CREATED).body(superAdminService.addRole(role));
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
            @Valid @RequestBody Privilege privilege
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(superAdminService.addPrivilege(privilege));
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

    @LogActivity
    @GetMapping(value = "/allPagesAdmins")
    public ResponseEntity<List<Admin>> getAllPagesAdmins() {
        return ResponseEntity.ok(superAdminService.getAllPagesAdmins());
    }
}
