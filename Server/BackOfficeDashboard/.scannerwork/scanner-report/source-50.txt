package com.majorMedia.BackOfficeDashboard.model.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BusinessTypeRequest {
    @NotEmpty(message = "Name cannot Be Empty")
    private String name ;
    private long businessCategoryId;
}
