package com.webserver.services;

import com.webserver.domain.MulticoinTransactionDTO;
import com.webserver.exceptions.UserException;
import com.webserver.models.User;
import com.webserver.repositories.PriceRepository;
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

    @Autowired
    private PriceRepository priceRepository;

    public void buy(MulticoinTransactionDTO multicoinTransactionDTO) throws IOException, JSONException {
        User user = userRepository.findById(multicoinTransactionDTO.getUserId());
        double amount = multicoinTransactionDTO.getAmount();
        if (user.getDollars() < amount) {
            throw new UserException("User doesn't have enough funds");
        }
        double price = priceRepository.findTopByOrderByDateDesc().getBuyPrice();
        double multicoins = amount / price;
        System.out.println("Will buy " + multicoins + " Multicoins for user " + user.getUsername());
        exchangeManager.buyCoins(multicoins);
        user.setDollars(user.getDollars() - amount);
        System.out.println("New user balance " + user.getUsername() + ": " + user.getDollars());
        userRepository.save(user);
        System.out.println("Send " + multicoins + " Multicoins to user " + user.getUsername() + " address: " + user.getMulticoinAddress());
        multichainManager.sendMulticoinsToAddress(user.getMulticoinAddress(), multicoins);
    }

    public void sell(MulticoinTransactionDTO multicoinTransactionDTO) throws IOException, JSONException {
        User user = userRepository.findById(multicoinTransactionDTO.getUserId());
        double amount = multicoinTransactionDTO.getAmount();
        double currentAmount = multichainManager.getUserBalance(user.getMulticoinAddress());
        if (amount > currentAmount) {
            throw new UserException("Not enough Multicoins");
        }
        double price = priceRepository.findTopByOrderByDateDesc().getSellPrice();
        double dollars = price * amount;
        System.out.println("Will sell " + amount + " Multicoins for user " + user.getUsername());
        exchangeManager.sellCoins(amount);
        user.setDollars(user.getDollars() + dollars);
        userRepository.save(user);
        System.out.println("New user balance " + user.getUsername() + ": " + user.getDollars());
        multichainManager.sendMulticoinsFromAddress(user.getMulticoinAddress(), amount);
        System.out.println("Send " + amount + " Multicoins from user " + user.getUsername() + " address: " + user.getMulticoinAddress());
    }

}
