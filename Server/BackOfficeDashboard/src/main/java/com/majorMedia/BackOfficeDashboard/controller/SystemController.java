package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessCategory;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.model.requests.BusinessTypeRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ServiceAreaRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateBusinessCategory;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateServiceCategory;
import com.majorMedia.BackOfficeDashboard.repository.ServiceAreaCategoryRepository;
import com.majorMedia.BackOfficeDashboard.service.IBusinessTypeService;
import com.majorMedia.BackOfficeDashboard.service.IServiceAreaService;
import com.majorMedia.BackOfficeDashboard.service.ServiceAreaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/config")
public class SystemController {

    private IServiceAreaService serviceAreaService ;
    private IBusinessTypeService businessTypeService;
    @LogActivity
    @GetMapping("/service-categories")
    public ResponseEntity<List<ServiceCategory>> getAllServiceCategories(){
        return new ResponseEntity<>(serviceAreaService.getAllServiceAreaCategories() , HttpStatus.OK);

    }
    @LogActivity
    @PostMapping("/service-categories")
    public ResponseEntity<List<ServiceCategory>> saveServiceCategories(@RequestBody List<ServiceCategory> serviceCategories ){
        return new ResponseEntity<>(serviceAreaService.saveServiceCategories(serviceCategories) , HttpStatus.CREATED);

    }
    @LogActivity
    @PostMapping("/service-category")
    public ResponseEntity<ServiceCategory> saveServiceCategory(@RequestBody ServiceCategory serviceCategory){
        return new ResponseEntity<>(serviceAreaService.saveServiceCategory(serviceCategory) , HttpStatus.CREATED);
    }
    @LogActivity
    @PutMapping("/service-category")
    public ResponseEntity<ServiceCategory> updateServiceCategory(@RequestBody UpdateServiceCategory serviceCategory){
        return new ResponseEntity<>(serviceAreaService.updateServiceCategory(serviceCategory) ,HttpStatus.ACCEPTED);
    }
    @LogActivity
    @PostMapping("/service-area")
    public ResponseEntity<ServiceArea> saveServiceArea(@Valid @RequestBody ServiceAreaRequest serviceArea){
        return new ResponseEntity<>(serviceAreaService.saveServiceArea(serviceArea) , HttpStatus.CREATED);

    }
    @LogActivity
    @DeleteMapping("/service-area/{id}")
    public ResponseEntity<HttpStatus> deleteServiceArea(@PathVariable long id){
        serviceAreaService.deleteServiceArea(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @LogActivity
    @DeleteMapping("/service-area")
    public ResponseEntity<HttpStatus> deleteServiceAreas(@RequestParam List<Long> serviceIds){
        serviceAreaService.deleteServiceAreas(serviceIds);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @LogActivity
    @GetMapping("/business-categories")
    public ResponseEntity<List<BusinessCategory>> getAllBussinessCategories(){
        return new ResponseEntity<>(businessTypeService.getAllBusinessCategories() , HttpStatus.OK);
    }
    @LogActivity
    @PostMapping("/business-category")
    public ResponseEntity<BusinessCategory> saveBusinessCategory(@RequestBody @Valid BusinessCategory businessCategory){

        return new ResponseEntity<>( businessTypeService.saveBusinessCategory(businessCategory), HttpStatus.CREATED);
    }
    @LogActivity
    @PatchMapping("/business-category")
    public ResponseEntity<BusinessCategory> updateBusinessCategory(@RequestBody @Valid UpdateBusinessCategory updateBusinessCategory) {
        BusinessCategory updatedCategory = businessTypeService.updateBusinessCategory(
                updateBusinessCategory.getCategoryId() , updateBusinessCategory.getName() , updateBusinessCategory.getDescription());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCategory);
    }
    @LogActivity
    @PostMapping("/business-type")
    public ResponseEntity<BusinessType> createBusinessType(@Valid @RequestBody BusinessTypeRequest businessTypeRequest){

        return new ResponseEntity<>(businessTypeService.createBusinessType(businessTypeRequest) , HttpStatus.CREATED);

    }
    @LogActivity
    @DeleteMapping("/business-type")
    public ResponseEntity<HttpStatus> deleteBusinessTypes(@RequestParam List<Long> businessIds ){
        businessTypeService.deleteBusinessTypes(businessIds);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }




}

