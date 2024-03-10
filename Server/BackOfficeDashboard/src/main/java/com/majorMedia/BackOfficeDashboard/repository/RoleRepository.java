package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.Admin;
import com.majorMedia.BackOfficeDashboard.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName(String name);
}
