package com.webserver.repositories;


import com.webserver.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Marin on 1/12/2017.
 */
public interface PriceRepository extends JpaRepository<Price, Integer> {

    Price findTopByOrderByDateDesc();

}
