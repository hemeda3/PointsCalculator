package com.mapledoum.pointscalc.service;

import com.mapledoum.pointscalc.model.ApiResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface IPointsService {
     ApiResponseDTO calcPointsForMonth(Long id, LocalDate month);
     ApiResponseDTO calcPointsTotalFromTo(Long customerId, LocalDate from, LocalDate to);

    int calcTierPoints(BigDecimal tierThreshold, BigDecimal amount, BigDecimal tier2MultiplierPoints);
}
