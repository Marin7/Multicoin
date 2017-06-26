package com.webserver.controllers;

import com.webserver.domain.CreditCardTransactionDTO;
import com.webserver.services.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Marin on 6/24/2017.
 */
@RestController
@CrossOrigin("3600")
@RequestMapping("withdraw")
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @RequestMapping(method = RequestMethod.POST)
    public void withdraw(@RequestBody CreditCardTransactionDTO creditCardTransactionDTO) {
        withdrawService.withdrawMoney(creditCardTransactionDTO);
    }
}
