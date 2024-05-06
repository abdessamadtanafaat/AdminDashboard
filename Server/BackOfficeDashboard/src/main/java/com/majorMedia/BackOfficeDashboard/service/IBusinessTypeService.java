package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.business.BusinessCategory;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import com.majorMedia.BackOfficeDashboard.model.requests.BusinessTypeRequest;

import java.util.List;

public interface IBusinessTypeService {
    List<BusinessCategory> getAllBusinessCategories();
    BusinessCategory saveBusinessCategory(BusinessCategory businessCategory);
    BusinessCategory updateBusinessCategory(Long categoryId , String name , String description);

    void deleteBusinessTypes(List<Long> businessIds);
    BusinessType createBusinessType(BusinessTypeRequest businessTypeRequest);

    class StatisctisService {
    }
}
