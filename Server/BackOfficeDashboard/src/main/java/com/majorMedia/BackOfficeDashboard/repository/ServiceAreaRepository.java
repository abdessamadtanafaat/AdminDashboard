package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceAreaRepository extends JpaRepository<ServiceArea , Long> {

    Optional<ServiceArea> findByNameIgnoreCase(String name);
}
