package com.webserver.services;

import com.webserver.domain.MulticoinTransactionDTO;
import com.webserver.exceptions.UserException;
import com.webserver.models.User;
import com.webserver.transactions.MultichainManager;
import com.webserver.repositories.UserRepository;
import com.webserver.transactions.ExchangeManager;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Marin on 6/26/2017.
 */
@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExchangeManager exchangeManager;

    @Autowired
    private MultichainManager multichainManager;

    public void buy(MulticoinTransactionDTO multicoinTransactionDTO) throws IOException, JSONException {
        User user = userRepository.findById(multicoinTransactionDTO.getUserId());
        double amount = multicoinTransactionDTO.getAmount();
        if (user.getDollars() < amount) {
            throw new UserException("User doesn't have enough funds");
        }
        double price = exchangeManager.getBuyPrice();
        double multicoins = amount / price;
        exchangeManager.buyCoins(multicoins);
        user.setDollars(user.getDollars() - amount);
        userRepository.save(user);
        multichainManager.sendMulticoinsToAddress(user.getMulticoinAddress(), multicoins);
    }

    public void sell(MulticoinTransactionDTO multicoinTransactionDTO) throws IOException, JSONException {
        User user = userRepository.findById(multicoinTransactionDTO.getUserId());
        double amount = multicoinTransactionDTO.getAmount();
        double currentAmount = multichainManager.getUserBalance(user.getMulticoinAddress());
        if (amount > currentAmount) {
            throw new RuntimeException("Not enough Multicoins");
        }
        double price = exchangeManager.getSellPrice();
        double dollars = price * amount;
        exchangeManager.sellCoins(amount);
        user.setDollars(user.getDollars() + dollars);
        userRepository.save(user);
        multichainManager.sendMulticoinsFromAddress(user.getMulticoinAddress(), amount);
    }

}
