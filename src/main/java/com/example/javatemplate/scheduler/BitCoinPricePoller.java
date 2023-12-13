package com.example.javatemplate.scheduler;

import com.example.javatemplate.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class BitCoinPricePoller {

    @Autowired
    DataService dataService;

    @Scheduled(fixedRate = 6000)
    public void coinDeskPricePoller()  {
        boolean fetchSuccess = dataService.fetchAndPersistCoinPrice();
        log.info("Coin price fetch success={}", fetchSuccess);

    }
}
