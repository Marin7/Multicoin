package com.example.demo.repositories;


import com.example.demo.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marin on 1/12/2017.
 */
public interface PriceRepository extends JpaRepository<Price, Integer> {

    Price findTopByOrderByDateDesc();

}
