package com.example.demo.services;

import com.example.demo.domain.PriceDTO;
import com.example.demo.exceptions.PriceException;
import com.example.demo.models.Price;
import com.example.demo.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Utils.getBuyPrice;
import static com.example.demo.Utils.getSellPrice;

/**
 * Created by Marin on 1/12/2017.
 */
@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public PriceDTO getLatestPrice() {
        Price price = priceRepository.findTopByOrderByDateDesc();
        if (price == null) {
            throw new PriceException("Error getting price");
        }

        PriceDTO dto = new PriceDTO.Builder()
                .id(price.getId())
                .sellPrice(getSellPrice(price.getDollars()))
                .buyPrice(getBuyPrice(price.getDollars()))
                .date(price.getDate())
                .create();
        return dto;
    }


    public List<PriceDTO> getPricesReport() {
        List<Price> prices = priceRepository.findAll();
        if (prices == null) {
            throw new PriceException("Error getting price");
        }

        List<PriceDTO> priceDTOS = new ArrayList<>();
        for (Price price : prices) {
            PriceDTO dto = new PriceDTO.Builder()
                    .id(price.getId())
                    .buyPrice(getBuyPrice(price.getDollars()))
                    .sellPrice(getSellPrice(price.getDollars()))
                    .date(price.getDate())
                    .create();
            priceDTOS.add(dto);
        }
        return priceDTOS;
    }
}
