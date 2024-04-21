package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaginationData {
    private int page ;
    private int pageSize ;
    private int pageCount ;
    private Long total ;
}
