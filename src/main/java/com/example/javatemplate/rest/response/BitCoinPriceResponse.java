package com.example.javatemplate.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BitCoinPriceResponse{
	private Time time;
	private String disclaimer;
	private String chartName;
	private Bpi bpi;

	@Data
	public static class Bpi{
		@JsonProperty("USD")
		private CoinPrice uSD;
		@JsonProperty("GBP")
		private CoinPrice gBP;
		@JsonProperty("EUR")
		private CoinPrice eUR;
	}

	@Data
	public static class CoinPrice{
		private String code;
		private String symbol;
		private String rate;
		private String description;
		@JsonProperty("rate_float")
		private Double rateFloat;
	}

	@Data
	public static class Time{
		private String updated;
		private String updatedISO;
		private String updateduk;
	}

}

