package com.majorMedia.BackOfficeDashboard.service.SystemService;

import com.majorMedia.BackOfficeDashboard.entity.business.BusinessCategory;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import com.majorMedia.BackOfficeDashboard.exception.AlreadyExistsEntityException;
import com.majorMedia.BackOfficeDashboard.exception.EntityNotFoundException;
import com.majorMedia.BackOfficeDashboard.model.requests.BusinessTypeRequest;
import com.majorMedia.BackOfficeDashboard.repository.BusinessCategoryRepository;
import com.majorMedia.BackOfficeDashboard.repository.BusinessTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusinessTypeService implements IBusinessTypeService{

    private BusinessCategoryRepository businessCategoryRepository ;
    private BusinessTypeRepository businessTypeRepository ;

    @Override
    public List<BusinessCategory> getAllBusinessCategories() {
        return businessCategoryRepository.findAll();
    }

    @Override
    public BusinessCategory saveBusinessCategory(BusinessCategory businessCategory) {
        boolean categoryExists = businessCategoryRepository.findByCategoryNameIgnoreCase(businessCategory.getCategoryName()).isPresent();
        if(categoryExists){
            throw new AlreadyExistsEntityException(businessCategory.getCategoryName() , BusinessCategory.class);
        }
        return businessCategoryRepository.save(businessCategory);
    }

    @Override
    public BusinessCategory updateBusinessCategory(Long categoryId , String name, String description) {
        Optional<BusinessCategory> businessCategory = businessCategoryRepository.findById(categoryId);
        BusinessCategory foundedBusinessCategory =unwrappBusinessCategory(businessCategory , categoryId);
        foundedBusinessCategory.setCategoryName(name);
        foundedBusinessCategory.setCategoryDesc(description);
        return businessCategoryRepository.save(foundedBusinessCategory);

    }
    public void deleteBusinessType(Long id) {
        BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(id , BusinessType.class)
        );
        businessTypeRepository.delete(businessType);
    }


    @Override
    public void deleteBusinessTypes(List<Long> businessIds) {
        for(Long id : businessIds){
            deleteBusinessType(id);
        }


    }

    @Override
    public BusinessType createBusinessType(BusinessTypeRequest businessTypeRequest) {
        BusinessCategory businessCategory = businessCategoryRepository.findById(businessTypeRequest.getBusinessCategoryId()).orElseThrow(
                ()->new EntityNotFoundException(businessTypeRequest.getBusinessCategoryId() ,  BusinessCategory.class)
        );
        if(businessTypeRepository.findByTypeNameIgnoreCase(businessTypeRequest.getName()).isPresent()){
            throw  new AlreadyExistsEntityException(businessTypeRequest.getName() , BusinessType.class);
        }
        BusinessType businessType = new BusinessType();
        businessType.setTypeName(businessTypeRequest.getName());
        businessType.setBusinessCategory(businessCategory);
        return businessTypeRepository.save(businessType);
    }

    public static BusinessCategory unwrappBusinessCategory(Optional<BusinessCategory> entity , Long categoryId){

        if(!entity.isPresent()){
            throw new EntityNotFoundException(categoryId , BusinessCategory.class);
        }
        return entity.get();

    }
}
