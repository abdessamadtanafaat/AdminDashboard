package com.majorMedia.BackOfficeDashboard.model.responses;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StatisctisData {
    private int usersNbr;
    private int businessNbr;
    private int customerNbr;
    private int compainsNbr;
    private Map<String, Integer> businessTypes;
    private Map<String , Integer> businesesCreated;
    private Map<String , Integer> campaignsCreated;


}
