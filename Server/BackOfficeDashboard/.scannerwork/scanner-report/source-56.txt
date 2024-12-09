package com.majorMedia.BackOfficeDashboard.model.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateServiceCategory {
    @NotNull(message="Name cannot be null")
    @NotEmpty(message="Name cannot be empty")
    private String name;

    @NotNull(message="Description cannot be null")
    @NotEmpty(message="Description cannot be empty")
    private String description;
    private Long serviceCategoryId;
}
