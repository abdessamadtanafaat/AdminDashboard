package com.majorMedia.BackOfficeDashboard.entity.campaign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.majorMedia.BackOfficeDashboard.entity.business.Business;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "campaign_service_area",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "service_area_id"))
    private List<ServiceArea> serviceAreas ;
    @JoinColumn(name = "campaignName", nullable = false)
    private String campaignName;


    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;
    @OneToMany(mappedBy = "campaign")
    private List<LoyaltyProgramme> loyaltyProgrammes;

    @ManyToMany
    @JoinTable(
            name = "compagne_language",
            joinColumns = @JoinColumn(name = "compagne_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @JsonIgnore
    private List<Language> languages;


    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Display> displays;



}
