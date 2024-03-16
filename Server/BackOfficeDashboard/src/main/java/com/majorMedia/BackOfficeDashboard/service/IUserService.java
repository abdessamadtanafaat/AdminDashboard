package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.model.responses.UserResponse;

import java.util.List;

public interface IUserService {
        public List<UserResponse> getAllUsers(String sortBy, String searchKey);
}


