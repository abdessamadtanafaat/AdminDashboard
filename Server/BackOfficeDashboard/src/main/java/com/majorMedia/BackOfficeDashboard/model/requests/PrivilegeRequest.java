package com.majorMedia.BackOfficeDashboard.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeRequest {
        private String name;
        private String description;

}
