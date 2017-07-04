package com.webserver.services;

import com.webserver.domain.PriceDTO;
import com.webserver.repositories.PriceRepository;
import com.webserver.transactions.ExchangeManager;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ExchangeManager exchangeManager;

    public PriceDTO getLatestPrice() throws IOException, JSONException {
        return new PriceDTO(new Date(), exchangeManager.getBuyPrice(), exchangeManager.getSellPrice());
    }

}
