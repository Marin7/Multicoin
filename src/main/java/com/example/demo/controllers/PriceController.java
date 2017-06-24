package com.example.demo.controllers;

import com.example.demo.domain.PriceDTO;
import com.example.demo.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public PriceDTO getLatestPrice() {
        PriceDTO priceDTO = priceService.getLatestPrice();
        return priceDTO;
    }

}
