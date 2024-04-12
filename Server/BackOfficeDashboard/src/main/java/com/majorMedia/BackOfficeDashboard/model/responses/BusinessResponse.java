package com.majorMedia.BackOfficeDashboard.model.responses;
import com.majorMedia.BackOfficeDashboard.entity.business.BusinessType;
import com.majorMedia.BackOfficeDashboard.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BusinessResponse {
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
    private BusinessType type;
    private User user;


}
