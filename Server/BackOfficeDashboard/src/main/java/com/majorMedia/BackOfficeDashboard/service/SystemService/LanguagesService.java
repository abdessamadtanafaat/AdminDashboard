package com.majorMedia.BackOfficeDashboard.service.SystemService;

import com.majorMedia.BackOfficeDashboard.entity.campaign.Language;
import com.majorMedia.BackOfficeDashboard.repository.CampaignRepository;
import com.majorMedia.BackOfficeDashboard.repository.LanguageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguagesService implements  ILanguagesService {
    private LanguageRepository languageRepository;
    private CampaignRepository campaignRepository;

    @Override
    public List<Language> getLangues() {

        return languageRepository.findAll();

    }
}
