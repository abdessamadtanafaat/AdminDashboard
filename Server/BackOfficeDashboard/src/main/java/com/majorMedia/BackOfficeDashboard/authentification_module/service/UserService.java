package com.majorMedia.BackOfficeDashboard.authentification_module.service;

import com.majorMedia.BackOfficeDashboard.authentification_module.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail (String email);
    User save(User user);
}
