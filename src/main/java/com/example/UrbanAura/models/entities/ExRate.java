package com.example.UrbanAura.models.entities;

import com.example.UrbanAura.models.BaseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;

@Entity
@Table(name = "ex_rates")
public class ExRate extends BaseEntity {

    @NotEmpty
    @Column(unique = true)
    private String currency;

    @Positive
    @NotNull
    private BigDecimal rate;

    @Override
    public String toString() {
        return "ExRate{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }

    public String getCurrency() {
        return currency;
    }

    public ExRate setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public BigDecimal getRate() {
        return this.rate;
    }

    public ExRate setRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }
}
