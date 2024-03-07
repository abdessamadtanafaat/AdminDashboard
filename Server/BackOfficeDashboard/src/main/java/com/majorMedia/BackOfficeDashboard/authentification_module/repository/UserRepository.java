package com.majorMedia.BackOfficeDashboard.authentification_module.repository;

import com.majorMedia.BackOfficeDashboard.authentification_module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
 }
