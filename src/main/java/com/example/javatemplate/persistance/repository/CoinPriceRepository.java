package com.example.javatemplate.persistance.repository;

import com.example.javatemplate.persistance.modal.CoinPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CoinPriceRepository extends JpaRepository<CoinPrice, Long> {
    @Query(value = "select * from coin_price where updated_at>=:start and updated_at<=:end", nativeQuery = true)
    List<CoinPrice> findPriceBetweenTime(Date start, Date end);
}