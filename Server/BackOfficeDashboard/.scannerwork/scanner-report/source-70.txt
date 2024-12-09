package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.business.BusinessCategory;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory , Long> {
    Optional<BusinessCategory> findByCategoryNameIgnoreCase(String name);


}
