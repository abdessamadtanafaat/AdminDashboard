package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.campaign.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language , Long> {

}
