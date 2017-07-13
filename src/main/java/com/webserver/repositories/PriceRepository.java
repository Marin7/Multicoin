package com.webserver.repositories;


import com.webserver.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Marin on 1/12/2017.
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    Price findTopByOrderByDateDesc();

}
