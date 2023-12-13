package com.example.javatemplate.rest.response;

import com.example.javatemplate.persistance.dto.CoinAnalysisDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CoinPriceData {
    List<CoinAnalysisDto> coinPriceList;
}

