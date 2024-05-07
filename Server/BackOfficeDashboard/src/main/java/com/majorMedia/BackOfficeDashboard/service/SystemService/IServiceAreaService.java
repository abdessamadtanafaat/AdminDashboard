package com.majorMedia.BackOfficeDashboard.service.SystemService;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.model.requests.ServiceAreaRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateServiceCategory;

import java.util.List;

public interface IServiceAreaService {
    List<ServiceCategory> getAllServiceAreaCategories();
    ServiceCategory updateServiceCategory(UpdateServiceCategory serviceCategory);
    List<ServiceCategory> saveServiceCategories(List<ServiceCategory> serviceCategories);
    ServiceCategory saveServiceCategory(ServiceCategory serviceCategory);
    ServiceArea saveServiceArea(ServiceAreaRequest serviceAreaRequest);
    void  deleteServiceArea(Long id);
    void deleteServiceAreas(List<Long> serviceIds );


}
