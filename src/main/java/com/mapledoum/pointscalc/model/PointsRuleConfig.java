package com.mapledoum.pointscalc.model;

import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PointsRuleConfig {


    private BigDecimal tierThreshold;
    private BigDecimal tier2MultiplierPoints;
}
