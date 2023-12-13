package com.example.javatemplate.service;

import com.example.javatemplate.persistance.dto.CoinAnalysisDto;
import com.example.javatemplate.persistance.modal.CoinPrice;
import com.example.javatemplate.persistance.modal.PriceAnalysis;
import com.example.javatemplate.persistance.repository.CoinPriceRepository;
import com.example.javatemplate.persistance.repository.PriceAnalysisRepository;
import com.example.javatemplate.rest.response.BitCoinPriceResponse;
import com.example.javatemplate.rest.response.CoinPriceData;
import com.example.javatemplate.rest.response.CoinPriceDto;
import com.example.javatemplate.rest.response.CoinPriceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DataService {
    @Autowired
    private CoinPriceRepository coinPriceRepository;

    @Autowired
    private PriceAnalysisRepository priceAnalysisRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${coindesk.bitcoin.api.url}")
    String bitcoinApiUrl;

    public CoinPriceResponse getBitCoinPrice(String startTime, String endTime) {
        try {
            Date startDateTime = parseStringToTime(startTime);
            Date endDateTime = parseStringToTime(endTime);

            List<CoinAnalysisDto> coinPriceList = coinPriceRepository.findPriceBetweenTime(startDateTime, endDateTime);

            CoinPriceData coinPriceData = CoinPriceData.builder()
                    .coinPriceList(coinPriceList)
                    .build();
            return CoinPriceResponse.builder()
                    .data(coinPriceData)
                    .success(true)
                    .build();
        } catch (Exception e) {
            log.error("Error while fetching bitcoin price from db", e);
            return CoinPriceResponse.builder()
                    .success(false)
                    .build();
        }
    }

    public boolean fetchAndPersistCoinPrice() {
        try {
            String response = restTemplate.getForObject(bitcoinApiUrl, String.class);
            BitCoinPriceResponse bitCoinPriceResponse = objectMapper.readValue(response, BitCoinPriceResponse.class);
            buildAndPersistPrice(bitCoinPriceResponse);
            return true;
        } catch (Exception e) {
            log.error("Error while fetching bitcoin price", e);
            return false;
        }
    }

    /**
     * $1 = 0.75 GBP = 0.9 EUR
     *
     * @param coinPriceResponse
     * @return
     */
    private void buildAndPersistPrice(BitCoinPriceResponse coinPriceResponse) {
        CoinPrice coinPrice = new CoinPrice();
        PriceAnalysis priceAnalysis = new PriceAnalysis();
        Date updatedAt = new Date();
        BitCoinPriceResponse.Bpi bpi = coinPriceResponse.getBpi();
        Double usdRate = bpi.getUSD().getRateFloat();
        Double gbpRate = bpi.getGBP().getRateFloat() * (1 / 0.75);
        Double eurRate = bpi.getEUR().getRateFloat() * (1/.9);

        coinPrice.setUsdRate(usdRate);
        coinPrice.setGbpRate(gbpRate);
        coinPrice.setEurRate(eurRate);
        coinPrice.setUpdatedAt(updatedAt);
        priceAnalysis.setUpdatedAt(updatedAt);
        if (usdRate > gbpRate && usdRate > eurRate) {
            priceAnalysis.setExpensiveCurrency("usd");
            priceAnalysis.setExpensiveRateInUsd(usdRate);
            if (gbpRate < eurRate) {
                priceAnalysis.setCheapestCurrency("gbp");
                priceAnalysis.setCheapestRateInUsd(gbpRate);
            } else {
                priceAnalysis.setCheapestCurrency("eur");
                priceAnalysis.setCheapestRateInUsd(eurRate);
            }
        } else if (gbpRate > eurRate) {
            priceAnalysis.setExpensiveCurrency("gbp");
            priceAnalysis.setExpensiveRateInUsd(gbpRate);
            if (eurRate < usdRate) {
                priceAnalysis.setCheapestCurrency("eur");
                priceAnalysis.setCheapestRateInUsd(eurRate);
            } else {
                priceAnalysis.setCheapestCurrency("usd");
                priceAnalysis.setCheapestRateInUsd(usdRate);
            }
        }else{
            priceAnalysis.setExpensiveCurrency("eur");
            priceAnalysis.setExpensiveRateInUsd(eurRate);
            if (gbpRate < usdRate) {
                priceAnalysis.setCheapestCurrency("gbp");
                priceAnalysis.setCheapestRateInUsd(gbpRate);
            } else {
                priceAnalysis.setCheapestCurrency("usd");
                priceAnalysis.setCheapestRateInUsd(usdRate);
            }
        }
        coinPriceRepository.save(coinPrice);
        priceAnalysisRepository.save(priceAnalysis);

    }

    CoinPriceDto buildCoinPriceDto(CoinAnalysisDto coinPrice) {
        return CoinPriceDto.builder()
                .usdRate(coinPrice.getUsdRate())
                .gbpRate(coinPrice.getGbpRate())
                .eurRate(coinPrice.getEurRate())
                .updatedAt(Objects.toString(coinPrice.getUpdatedAt(), ""))
                .build();
    }

    private Date parseStringToTime(String timeString) {
        try {
            LocalDate tody = LocalDate.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(timeString, timeFormatter);
            ZonedDateTime dateTime = tody.atTime(time).atZone(ZoneId.systemDefault());
            return Date.from(dateTime.toInstant());

        } catch (Exception e) {
            log.error("Failed to parse time={}", timeString, e);
            return null;
        }
    }
}
