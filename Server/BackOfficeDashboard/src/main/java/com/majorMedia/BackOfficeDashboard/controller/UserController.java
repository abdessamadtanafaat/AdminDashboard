package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;
import com.majorMedia.BackOfficeDashboard.service.IUserService;
import com.majorMedia.BackOfficeDashboard.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserServiceImpl userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>>  getAllUsers(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String searchKey)
    {
        List<UserResponse> userResponses = userService.getAllUsers(sortBy,searchKey);
        return ResponseEntity.ok(userResponses);
    }



}
