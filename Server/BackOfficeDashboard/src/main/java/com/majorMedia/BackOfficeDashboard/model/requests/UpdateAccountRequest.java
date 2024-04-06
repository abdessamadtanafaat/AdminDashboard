package com.majorMedia.BackOfficeDashboard.model.requests;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {
    private String email;
    private String firstname;
    private String lastname;
}
