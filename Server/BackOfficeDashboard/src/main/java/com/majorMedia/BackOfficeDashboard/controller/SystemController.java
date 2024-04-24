package com.majorMedia.BackOfficeDashboard.controller;

import com.majorMedia.BackOfficeDashboard.aspect.LogActivity;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceArea;
import com.majorMedia.BackOfficeDashboard.entity.campaign.ServiceCategory;
import com.majorMedia.BackOfficeDashboard.repository.ServiceAreaCategoryRepository;
import com.majorMedia.BackOfficeDashboard.service.IServiceAreaService;
import com.majorMedia.BackOfficeDashboard.service.ServiceAreaService;
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



}
