package com.webserver.controllers;

import com.webserver.domain.TransactionDTO;
import com.webserver.services.TransactionService;
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
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST)
    public void withdraw(@RequestBody TransactionDTO transactionDTO) {
        transactionService.withdrawMoney(transactionDTO);
    }
}
