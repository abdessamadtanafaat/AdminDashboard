package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

    @Repository
public interface CampagneRepository extends JpaRepository<Campaign, Integer> {
        Optional<Campaign> findById(int id);
        Page<Campaign> findAllByCampaignNameContainsIgnoreCase(String campaignName, Pageable page);

    }
