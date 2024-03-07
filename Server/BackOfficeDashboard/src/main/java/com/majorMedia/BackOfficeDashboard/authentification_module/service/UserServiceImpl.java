package com.majorMedia.BackOfficeDashboard.authentification_module.service;

import com.majorMedia.BackOfficeDashboard.authentification_module.entity.User;
import com.majorMedia.BackOfficeDashboard.authentification_module.repository.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User save(User user) {
        return  userRepository.save(user);
    }
}
