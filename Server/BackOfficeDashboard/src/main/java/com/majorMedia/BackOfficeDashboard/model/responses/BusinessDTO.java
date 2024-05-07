package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusinessDTO {
    private int id;
    private String businessName;
    private String email;
    private String phone;
    private String address;
    private String facebookLink;
    private String instagramLink;
    private String googleLink;
    private String coverImageUrl;
    private LocalDateTime createdDate;


    private Long idUser;
    private String username;
    private String fullName;
    private String firstName;
    private String lastName;
    private String emailUser;
    private String phoneUser;
}
