package com.webserver.controllers;

import com.webserver.domain.PriceDTO;
import com.webserver.services.PriceService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Marin on 1/12/2017.
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @RequestMapping(method = RequestMethod.GET)
    public PriceDTO getLatestPrice() throws IOException, JSONException {
        return priceService.getLatestPrice();
    }

}
