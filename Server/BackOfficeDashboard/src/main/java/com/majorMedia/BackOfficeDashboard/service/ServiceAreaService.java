package com.majorMedia.BackOfficeDashboard.service;

import com.majorMedia.BackOfficeDashboard.entity.admin.Admin;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistsEntityException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.requests.ServiceAreaRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateServiceCategory;
import com.majorMedia.BackOfficeDashboard.repository.ServiceAreaCategoryRepository;
import com.majorMedia.BackOfficeDashboard.repository.ServiceAreaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public ServiceCategory updateServiceCategory(UpdateServiceCategory updateServiceCategory) {
        Optional<ServiceCategory> serviceCategory = serviceAreaCategoryRepository.findById(updateServiceCategory.getServiceCategoryId());
        if(!serviceCategory.isPresent()){
            throw new  EntityNotFoundException(updateServiceCategory.getServiceCategoryId() , ServiceCategory.class);
        }
        ServiceCategory foundedServiceCategory =serviceCategory.get() ;
        foundedServiceCategory.setName(updateServiceCategory.getName());
        foundedServiceCategory.setDescription(updateServiceCategory.getDescription());
        return serviceAreaCategoryRepository.save(foundedServiceCategory);
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

    @Override
    public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory) {
        boolean categoryExists = serviceAreaCategoryRepository.findByNameIgnoreCase(serviceCategory.getName()).isPresent();
        if(categoryExists){
            throw new EntityNotFoundException(ServiceCategory.class);
        }

        return serviceAreaCategoryRepository.save(serviceCategory);
    }

    @Override
    public ServiceArea saveServiceArea(ServiceAreaRequest serviceAreaRequest) {
        ServiceCategory serviceCategory = serviceAreaCategoryRepository.findById(serviceAreaRequest.getServiceCategoryId()).orElseThrow(
                ()->new EntityNotFoundException(serviceAreaRequest.getServiceCategoryId(), ServiceCategory.class)
        );
        if(seriviceAreaRepository.findByNameIgnoreCase(serviceAreaRequest.getName()).isPresent()){
            throw new AlreadyExistsEntityException(serviceAreaRequest.getName(),ServiceArea.class);
        }
        ServiceArea serviceArea = new ServiceArea();
        serviceArea.setName(serviceAreaRequest.getName());
        serviceArea.setPrivate(false);
        serviceArea.setServiceCategory(serviceCategory);
        return seriviceAreaRepository.save(serviceArea);
    }

    @Override
    public void deleteServiceArea(Long id) {
        ServiceArea serviceArea = seriviceAreaRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(id , ServiceArea.class)
        );
        seriviceAreaRepository.delete(serviceArea);
    }

    @Override
    public void deleteServiceAreas(List<Long> serviceIds) {
        for (Long id : serviceIds) {
            deleteServiceArea(id);
        }

    }
}
