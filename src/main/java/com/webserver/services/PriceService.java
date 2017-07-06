package com.webserver.services;

import com.webserver.domain.PriceDTO;
import com.webserver.models.Price;
import com.webserver.repositories.PriceRepository;
import com.webserver.transactions.ExchangeManager;
import javafx.util.Pair;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Marin on 1/12/2017.
 */
@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public PriceDTO getLatestPrice() throws IOException, JSONException {
        Price latestPrice = priceRepository.findTopByOrderByDateDesc();
        return new PriceDTO(new Date(), latestPrice.getBuyPrice(), latestPrice.getSellPrice());
    }

    @Component
    public static class PriceUpdater {

        @Autowired
        private ExchangeManager exchangeManager;

        @Autowired
        private PriceRepository priceRepository;

        @Scheduled(fixedRate = 120000)
        public void updatePrices() {
            System.out.println("Update price");
            Pair<Double, Double> prices = null;
            try {
                prices = exchangeManager.getPrice();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            Price price = new Price();
            price.setBuyPrice(prices.getKey());
            price.setSellPrice(prices.getValue());
            price.setDate(new Date());
            priceRepository.save(price);
        }
    }

}
