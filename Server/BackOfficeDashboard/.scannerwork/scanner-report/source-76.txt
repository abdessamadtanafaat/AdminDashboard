package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.campaign.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language , Long> {
    @Query("SELECT DISTINCT l FROM Language l JOIN FETCH l.compagnes")
    List<Language> findAllLanguagesWithCampaigns();
}
