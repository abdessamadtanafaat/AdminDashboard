package com.majorMedia.BackOfficeDashboard.security.BlacklistToken;


import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistToken, Long> {
    boolean existsByToken(String token);
}