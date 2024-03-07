package com.majorMedia.BackOfficeDashboard.authentification_module.repository;


import com.majorMedia.BackOfficeDashboard.authentification_module.model.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long> {
    ForgotPasswordToken findByToken(String token);
}
