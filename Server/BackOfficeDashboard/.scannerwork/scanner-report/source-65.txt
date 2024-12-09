package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PermissionsResponse {
    private Long id;

    private String name;

    private String description;
}
