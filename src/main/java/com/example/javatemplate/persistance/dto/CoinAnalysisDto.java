package com.example.javatemplate.persistance.dto;

public interface CoinAnalysisDto {

    public String getCheapestCurrency();
    public Double getCheapestRateInUsd();
    public String getExpensiveCurrency();
    public Double getExpensiveRateInUsd();
    public String getUpdatedAt();
    public Double getUsdRate();
    public Double getGbpRate();
    public Double getEurRate();

}