package com.majorMedia.BackOfficeDashboard.service.SystemService;

import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Campaign;
import com.majorMedia.BackOfficeDashboard.model.responses.StatisctisData;
import com.majorMedia.BackOfficeDashboard.repository.BusinessRepository;
import com.majorMedia.BackOfficeDashboard.repository.CampaignRepository;
import com.majorMedia.BackOfficeDashboard.repository.CustomerRepository;
import com.majorMedia.BackOfficeDashboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class StatisticsService implements  IStatisticsService {
    private BusinessRepository businessRepository;
    private UserRepository userRepository ;
    private CampaignRepository campaignRepository ;
    private CustomerRepository customerRepository;


    @Override
    public StatisctisData getSystemData() {



        return StatisctisData.builder().
                businessNbr((int) businessRepository.count()).
                usersNbr((int) userRepository.count()).
                compainsNbr((int)campaignRepository.count()).
                customerNbr((int) customerRepository.count()).
                businessTypes(getBusinessBarData()).
                businesesCreated(getBusinessLineData()).
                campaignsCreated(getCampaignLineData()).


                
                build();
    }

    public Map<String, Integer> getBusinessLineData() {
        List<Business> businesses = businessRepository.findAll();

        Map<String, Integer> businessData = new TreeMap<>();
        for (Business business : businesses) {
            LocalDate creationDate = business.getCreatedDate().toLocalDate();
            String dateString = creationDate.toString();
            businessData.put(dateString, businessData.getOrDefault(dateString, 0) + 1);
        }

        return businessData;
    }
    public Map<String, Integer> getCampaignLineData() {
        List<Campaign> campaigns = campaignRepository.findAll();

        // Use TreeMap to automatically sort by date
        Map<String, Integer> campaignData = new TreeMap<>();
        for (Campaign campaign : campaigns) {
            LocalDate creationDate = campaign.getCreatedDate().toLocalDate();
            String dateString = creationDate.toString();
            campaignData.put(dateString, campaignData.getOrDefault(dateString, 0) + 1);
        }

        return campaignData;
    }
    public Map<String, Integer> getBusinessBarData() {
        List<Business> businesses = businessRepository.findAll();

        // Count the number of businesses for each type
        Map<String, Integer> businessData = new HashMap<>();
        for (Business business : businesses) {
            BusinessType type = business.getType();
            String typeName = type.getTypeName();
            businessData.put(typeName, businessData.getOrDefault(typeName, 0) + 1);
        }

        return businessData;
    }

}
