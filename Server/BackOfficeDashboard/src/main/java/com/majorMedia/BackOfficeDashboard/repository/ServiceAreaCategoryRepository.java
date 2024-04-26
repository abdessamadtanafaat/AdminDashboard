package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface ServiceAreaCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    Optional<ServiceCategory> findByNameIgnoreCase(String name);

}
