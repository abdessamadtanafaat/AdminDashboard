package com.majorMedia.BackOfficeDashboard.model.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBusinessCategory {
    private Long categoryId ;
    @NotEmpty(message="Name cannot be empty")

    private String name  ;
    @NotEmpty(message = "Description cannot be empty")
    private String description ;

}
