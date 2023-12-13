package com.example.javatemplate.persistance.repository;

import com.example.javatemplate.persistance.modal.CoinPrice;
import com.example.javatemplate.persistance.modal.PriceAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PriceAnalysisRepository extends JpaRepository<PriceAnalysis, Long> {
}