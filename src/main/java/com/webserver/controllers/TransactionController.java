package com.webserver.controllers;

import com.webserver.domain.MulticoinTransactionDTO;
import com.webserver.services.TransactionService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Marin on 6/26/2017.
 */
@CrossOrigin(value = "3600")
@RestController
@RequestMapping(value = "transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public void buy(@RequestBody MulticoinTransactionDTO multicoinTransactionDTO) throws IOException, JSONException {
        transactionService.buy(multicoinTransactionDTO);
    }

    @RequestMapping(value = "/sell", method = RequestMethod.POST)
    public void sell(@RequestBody MulticoinTransactionDTO multicoinTransactionDTO) throws IOException, JSONException {
        transactionService.sell(multicoinTransactionDTO);
    }

}
