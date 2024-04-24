package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;

import java.util.List;

public interface IServiceAreaService {
    List<ServiceCategory> getAllServiceAreaCategories();
    List<ServiceCategory> saveServiceCategories(List<ServiceCategory> serviceCategories);

}
