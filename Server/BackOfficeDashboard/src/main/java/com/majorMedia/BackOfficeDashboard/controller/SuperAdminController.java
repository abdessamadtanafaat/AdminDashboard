package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.admin.Privilege;
import com.majorMedia.BackOfficeDashboard.entity.admin.Role;
import com.majorMedia.BackOfficeDashboard.model.requests.*;
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
    public ResponseEntity<AdminResponse> getAdminDetails(@PathVariable Long adminId){
        AdminResponse adminResponse = superAdminService.getAdminDetails(adminId);
        return ResponseEntity.ok(adminResponse);
    }

    @PostMapping("/create-role")
    public ResponseEntity<Role> createRole(@RequestBody RoleRequest roleRequest)
    {
        Role role = superAdminService.addRole(roleRequest.getName(), roleRequest.getDescription());
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
    @PostMapping("/assignRole")
    public ResponseEntity<Admin> assignRole(@RequestBody AssignRoleRequest assignRoleRequest)
    {
        Admin admin = superAdminService.assignRoleToAdmin(assignRoleRequest.getAdminId(),
                                                          assignRoleRequest.getRoleId()
        );
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/create-privilege")
    public ResponseEntity<Privilege> createRole(@RequestBody PrivilegeRequest privilegeRequest)
    {
        Privilege privilege = superAdminService.addPrivilege(privilegeRequest.getName(), privilegeRequest.getDescription());
        return new ResponseEntity<>(privilege, HttpStatus.CREATED);
    }

    @PostMapping("/assignPrivilegeToRole")
    public ResponseEntity<String> assignPrivilegeToRole(@RequestBody AssignPrivilegeToRoleRequest assignPrivilegeToRoleRequest)
    {
        String string = superAdminService.assignPrivilegesToRole(assignPrivilegeToRoleRequest.getRoleId(),
                assignPrivilegeToRoleRequest.getPrivilegeIds()
        );
        return ResponseEntity.ok(string);
    }

}
