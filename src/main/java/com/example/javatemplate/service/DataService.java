package com.example.javatemplate.service;

import com.example.javatemplate.persistance.modal.CoinPrice;
import com.example.javatemplate.persistance.repository.CoinPriceRepository;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataService {
    @Autowired
    private CoinPriceRepository coinPriceRepository;

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

            List<CoinPriceDto> coinPriceList = coinPriceRepository.findPriceBetweenTime(startDateTime, endDateTime)
                    .stream()
                    .map(this::buildCoinPriceDto)
                    .collect(Collectors.toList());

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
            CoinPrice coinPrice = buildCoinPrice(bitCoinPriceResponse);
            coinPriceRepository.save(coinPrice);
            return true;
        } catch (Exception e) {
            log.error("Error while fetching bitcoin price", e);
            return false;
        }
    }

    private CoinPrice buildCoinPrice(BitCoinPriceResponse coinPriceResponse) {
        CoinPrice coinPrice = new CoinPrice();
        BitCoinPriceResponse.Bpi bpi = coinPriceResponse.getBpi();
        coinPrice.setUsdRate(bpi.getUSD().getRateFloat());
        coinPrice.setGbpRate(bpi.getGBP().getRateFloat());
        coinPrice.setEurRate(bpi.getEUR().getRateFloat());
        coinPrice.setUpdatedAt(new Date());
        return coinPrice;
    }

    CoinPriceDto buildCoinPriceDto(CoinPrice coinPrice) {
        return CoinPriceDto.builder()
                .usdRate(coinPrice.getUsdRate())
                .gbpRate(coinPrice.getGbpRate())
                .eurRate(coinPrice.getEurRate())
                .updatedAt(Objects.toString(coinPrice.getUpdatedAt(), ""))
                .build();
    }
    private Date parseStringToTime(String timeString){
        try {
            LocalDate tody = LocalDate.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(timeString, timeFormatter);
            ZonedDateTime dateTime = tody.atTime(time).atZone(ZoneId.systemDefault());
            return Date.from(dateTime.toInstant());

        }catch (Exception e){
            log.error("Failed to parse time={}", timeString, e);
            return null;
        }
    }
}
