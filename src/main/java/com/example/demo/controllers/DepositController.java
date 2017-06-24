package com.example.demo.controllers;

import com.example.demo.domain.TransactionDTO;
import com.example.demo.services.TransactionService;
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
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST)
    public void deposit(@RequestBody TransactionDTO transactionDTO) {
        transactionService.depositMoney(transactionDTO);
    }

}
