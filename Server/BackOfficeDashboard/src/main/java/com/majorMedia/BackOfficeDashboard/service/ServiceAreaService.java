package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.repository.ServiceAreaCategoryRepository;
import com.majorMedia.BackOfficeDashboard.repository.ServiceAreaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ServiceAreaService implements IServiceAreaService{
    private ServiceAreaCategoryRepository serviceAreaCategoryRepository ;
    private ServiceAreaRepository seriviceAreaRepository;
    @Override
    public List<ServiceCategory> getAllServiceAreaCategories() {
        return serviceAreaCategoryRepository.findAll();

    }

    @Override
    public List<ServiceCategory> saveServiceCategories(List<ServiceCategory> serviceCategories) {
        List<ServiceCategory> savedServiceCategories = new ArrayList<>();

        for (ServiceCategory serviceCategory : serviceCategories) {
            // Save each ServiceCategory first to get its ID
            ServiceCategory savedServiceCategory = serviceAreaCategoryRepository.save(serviceCategory);

            // Set the ServiceCategory reference for each ServiceArea


            for (ServiceArea serviceArea : savedServiceCategory.getServiceAreas()) {
                    serviceArea.setServiceCategory(savedServiceCategory);
                    seriviceAreaRepository.save(serviceArea);
            }
            savedServiceCategories.add(savedServiceCategory);
        }

        return savedServiceCategories;
    }
}
