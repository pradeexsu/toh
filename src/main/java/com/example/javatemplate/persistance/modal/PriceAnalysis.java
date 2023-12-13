package com.example.javatemplate.persistance.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "price_analysis")
public class PriceAnalysis {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cheapest_currency")
    private String cheapestCurrency;

    @Column(name = "cheapest_rate_in_usd")
    private Double cheapestRateInUsd;

    @Column(name = "expensive_currency")
    private String expensiveCurrency;

    @Column(name = "expensive_rate_in_usd")
    private Double expensiveRateInUsd;

    @Column(name = "updated_at")
    private Date updatedAt;
}