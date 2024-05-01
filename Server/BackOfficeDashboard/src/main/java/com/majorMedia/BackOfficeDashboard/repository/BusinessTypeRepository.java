package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType,Long > {

    Optional<BusinessType> findByTypeNameIgnoreCase(String typeName);
}
