package com.example.javatemplate.persistance.repository;

import com.example.javatemplate.persistance.dto.CoinAnalysisDto;
import com.example.javatemplate.persistance.modal.CoinPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CoinPriceRepository extends JpaRepository<CoinPrice, Long> {
    @Query(value = "select cp.usdRate as usdRate, cp.gbpRate as gbpRate, cp.eurRate as eurRate, cp.updatedAt as updatedAt, pa.cheapestCurrency as cheapestCurrency, pa.cheapestRateInUsd as cheapestRateInUsd, pa.expensiveCurrency as expensiveCurrency, pa.expensiveRateInUsd as expensiveRateInUsd from CoinPrice cp join PriceAnalysis pa on cp.updatedAt=pa.updatedAt where cp.updatedAt>=:start and cp.updatedAt<=:end")
    List<CoinAnalysisDto> findPriceBetweenTime(Date start, Date end);
}