package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.model.requests.ServiceAreaRequest;

import java.util.List;

public interface IServiceAreaService {
    List<ServiceCategory> getAllServiceAreaCategories();
    List<ServiceCategory> saveServiceCategories(List<ServiceCategory> serviceCategories);
    ServiceCategory saveServiceCategory(ServiceCategory serviceCategory);
    ServiceArea saveServiceArea(ServiceAreaRequest serviceAreaRequest);
    void  deleteServiceArea(Long id);


}
