package com.example.UrbanAura.repositories;

import com.example.UrbanAura.models.entities.ExRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExRatesRepository extends JpaRepository<ExRate, Long> {

    Optional<ExRate> findByCurrency(String currency);

//    @Query("SELECT e.currency FROM ExRate e")
//    List<ExRate> findAllCurrencies();
}
