package com.webserver.controllers;

import com.webserver.domain.CreditCardTransactionDTO;
import com.webserver.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Marin on 6/24/2017.
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("deposit")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @RequestMapping(method = RequestMethod.POST)
    public void deposit(@RequestBody CreditCardTransactionDTO creditCardTransactionDTO) {
        depositService.depositMoney(creditCardTransactionDTO);
    }

}
