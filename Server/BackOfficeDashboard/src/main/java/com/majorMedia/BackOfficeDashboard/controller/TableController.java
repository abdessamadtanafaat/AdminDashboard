package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import com.majorMedia.BackOfficeDashboard.model.responses.BusinessResponse;
import com.majorMedia.BackOfficeDashboard.model.responses.ObjectsList;
import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.adminService.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tables")


@AllArgsConstructor

//@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")

public class TableController {

    private final IAdminService adminService;
    @LogActivity
    @GetMapping(value = "/owners")
    public ResponseEntity<ObjectsList<User>> getAllUsers(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required=false ,name="page" , defaultValue="1") int page

    )
    {
        return ResponseEntity.ok(adminService.getAllOwners(searchKey,sortBy,page));
    }

    @LogActivity
    @GetMapping(value = "/business")
    public ResponseEntity<ObjectsList<Business>> getAllBusiness(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required=false ,name="page" , defaultValue="1") int page
    )
    {
        return ResponseEntity.ok(adminService.getAllBusiness(searchKey,sortBy,page));
    }
}
