package com.webserver.services;

import com.webserver.domain.TransactionDTO;
import com.webserver.exceptions.UserException;
import com.webserver.models.User;
import com.webserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Marin on 6/24/2017.
 */
@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    private final TransferManager transferManager = new TransferManager();

    public void depositMoney(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId());
        if (user == null) {
            throw new UserException("No user with id: " + transactionDTO.getUserId());
        }
        if (transferManager.transferMoneyFromCreditCard(transactionDTO.getCard(), transactionDTO.getAmount())) {
            buyCoins(transactionDTO.getAmount());
            user.setDollars(user.getDollars() + transactionDTO.getAmount());
            userRepository.save(user);
        }
    }

    public void withdrawMoney(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId());
        if (user == null) {
            throw new UserException("No user with id: " + transactionDTO.getUserId());
        }
        if (transferManager.transferMoneyToCreditCard(transactionDTO.getCard(), transactionDTO.getAmount())) {
            sellCoins(transactionDTO.getAmount());
            user.setDollars(user.getDollars() - transactionDTO.getAmount());
            userRepository.save(user);
        }
    }

    private void buyCoins(double amount) {
        ExchangeManager exchangeManager = new ExchangeManager("DAA9AD52FD11E65D8C1131FF323363CE", "5FB0EDB6FAADF61C058912D07C05FA05");
        exchangeManager.buyCoins(amount);
    }

    private void sellCoins(double amount) {
        ExchangeManager exchangeManager = new ExchangeManager("DAA9AD52FD11E65D8C1131FF323363CE", "5FB0EDB6FAADF61C058912D07C05FA05");
        exchangeManager.sellCoins(amount);
    }

}
