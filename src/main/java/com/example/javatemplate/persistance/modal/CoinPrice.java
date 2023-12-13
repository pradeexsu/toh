package com.example.javatemplate.persistance.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "coin_price")
public class CoinPrice {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usd_rate")
    private Double usdRate;

    @Column(name = "gbp_rate")
    private Double gbpRate;

    @Column(name = "eur_rate")
    private Double eurRate;

    @Column(name = "updated_at")
    private Date updatedAt;
}