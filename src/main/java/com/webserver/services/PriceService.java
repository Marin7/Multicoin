package com.webserver.services;

import com.webserver.domain.PriceDTO;
import com.webserver.exceptions.PriceException;
import com.webserver.models.Price;
import com.webserver.repositories.PriceRepository;
import com.webserver.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                .sellPrice(Utils.getSellPrice(price.getDollars()))
                .buyPrice(Utils.getBuyPrice(price.getDollars()))
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
                    .buyPrice(Utils.getBuyPrice(price.getDollars()))
                    .sellPrice(Utils.getSellPrice(price.getDollars()))
                    .date(price.getDate())
                    .create();
            priceDTOS.add(dto);
        }
        return priceDTOS;
    }
}
