package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessCategory;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import com.majorMedia.BackOfficeDashboard.entity.campaign.Language;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.model.requests.BusinessTypeRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.ServiceAreaRequest;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateBusinessCategory;
import com.majorMedia.BackOfficeDashboard.model.requests.UpdateServiceCategory;
import com.majorMedia.BackOfficeDashboard.model.responses.StatisctisData;
import com.majorMedia.BackOfficeDashboard.service.SystemService.IBusinessTypeService;
import com.majorMedia.BackOfficeDashboard.service.SystemService.ILanguagesService;
import com.majorMedia.BackOfficeDashboard.service.SystemService.IServiceAreaService;
import com.majorMedia.BackOfficeDashboard.service.SystemService.IStatisticsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/config")
public class SystemController {

    private IServiceAreaService serviceAreaService ;
    private IBusinessTypeService businessTypeService;
    private IStatisticsService statisticsService;
    private ILanguagesService languagesService;
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/service-categories")
    public ResponseEntity<List<ServiceCategory>> getAllServiceCategories(){
        return new ResponseEntity<>(serviceAreaService.getAllServiceAreaCategories() , HttpStatus.OK);

    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/service-categories")
    public ResponseEntity<List<ServiceCategory>> saveServiceCategories(@RequestBody List<ServiceCategory> serviceCategories ){
        return new ResponseEntity<>(serviceAreaService.saveServiceCategories(serviceCategories) , HttpStatus.CREATED);

    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/service-category")
    public ResponseEntity<ServiceCategory> saveServiceCategory(@RequestBody ServiceCategory serviceCategory){
        return new ResponseEntity<>(serviceAreaService.saveServiceCategory(serviceCategory) , HttpStatus.CREATED);
    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/service-category")
    public ResponseEntity<ServiceCategory> updateServiceCategory(@RequestBody UpdateServiceCategory serviceCategory){
        return new ResponseEntity<>(serviceAreaService.updateServiceCategory(serviceCategory) ,HttpStatus.ACCEPTED);
    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/service-area")
    public ResponseEntity<ServiceArea> saveServiceArea(@Valid @RequestBody ServiceAreaRequest serviceArea){
        return new ResponseEntity<>(serviceAreaService.saveServiceArea(serviceArea) , HttpStatus.CREATED);

    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/service-area/{id}")
    public ResponseEntity<HttpStatus> deleteServiceArea(@PathVariable long id){
        serviceAreaService.deleteServiceArea(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/service-area")
    public ResponseEntity<HttpStatus> deleteServiceAreas(@RequestParam List<Long> serviceIds){
        serviceAreaService.deleteServiceAreas(serviceIds);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/business-categories")
    public ResponseEntity<List<BusinessCategory>> getAllBussinessCategories(){
        return new ResponseEntity<>(businessTypeService.getAllBusinessCategories() , HttpStatus.OK);
    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/business-category")
    public ResponseEntity<BusinessCategory> saveBusinessCategory(@RequestBody @Valid BusinessCategory businessCategory){

        return new ResponseEntity<>( businessTypeService.saveBusinessCategory(businessCategory), HttpStatus.CREATED);
    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PatchMapping("/business-category")
    public ResponseEntity<BusinessCategory> updateBusinessCategory(@RequestBody @Valid UpdateBusinessCategory updateBusinessCategory) {
        BusinessCategory updatedCategory = businessTypeService.updateBusinessCategory(
                updateBusinessCategory.getCategoryId() , updateBusinessCategory.getName() , updateBusinessCategory.getDescription());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCategory);
    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/business-type")
    public ResponseEntity<BusinessType> createBusinessType(@Valid @RequestBody BusinessTypeRequest businessTypeRequest){

        return new ResponseEntity<>(businessTypeService.createBusinessType(businessTypeRequest) , HttpStatus.CREATED);

    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/business-type")
    public ResponseEntity<HttpStatus> deleteBusinessTypes(@RequestParam List<Long> businessIds ) {
        businessTypeService.deleteBusinessTypes(businessIds);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
    @LogActivity
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getLangues(){
        return new ResponseEntity<>(languagesService.getLangues(),  HttpStatus.OK);

    }
    @LogActivity
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StatisctisData> getSystemData(){
        return new ResponseEntity<>(statisticsService.getSystemData() , HttpStatus.OK);
    }


}

