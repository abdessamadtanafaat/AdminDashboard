package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign , Long> {
    List<Campaign> findAllByCreatedDateAfter(LocalDate date);

    List<Campaign> findAllByCreatedDateAfterAndCreatedDateBefore(LocalDate dateAfter  ,LocalDate dateBefore);

    List<Campaign> findByCreatedDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);
//    @Query("SELECT c FROM Campaign c JOIN '' cl ON c.id = cl.campaign.id WHERE cl.language.id = :languageId")
//    List<Campaign> findCampaignsByLanguageId(Long languageId);
}
