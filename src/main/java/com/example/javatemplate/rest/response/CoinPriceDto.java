package com.example.javatemplate.rest.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoinPriceDto {
	Double usdRate;
	Double gbpRate;
	Double eurRate;
	String updatedAt;
}

