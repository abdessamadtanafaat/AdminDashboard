package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ObjectsList<T> {
    private List<T> data;
    private PaginationData meta ;
}
