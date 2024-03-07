package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail (String email);
    User save(User user);
}
