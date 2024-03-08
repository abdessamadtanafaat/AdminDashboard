package com.majorMedia.BackOfficeDashboard.authentification_module.repository;

import com.majorMedia.BackOfficeDashboard.authentification_module.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByTokenEmail(String token);
    Optional<Admin> findByTokenWeb(String token);

}
