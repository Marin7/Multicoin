package com.webserver.services;

import com.webserver.domain.CreditCardDTO;
import com.webserver.domain.TransactionDTO;
import com.webserver.exceptions.UserException;
import com.webserver.models.User;
import com.webserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Marin on 6/24/2017.
 */
@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    public void depositMoney(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId());
        if (user == null) {
            throw new UserException("No user with id: " + transactionDTO.getUserId());
        }
        transferFromCreditCard(transactionDTO.getCard(), transactionDTO.getAmount());
        user.setDollars(user.getDollars() + transactionDTO.getAmount());
        userRepository.save(user);
    }

    public void withdrawMoney(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId());
        if (user == null) {
            throw new UserException("No user with id: " + transactionDTO.getUserId());
        }
        withdrawToCreditCard(transactionDTO.getCard(), transactionDTO.getAmount());
        user.setDollars(user.getDollars() - transactionDTO.getAmount());
        userRepository.save(user);
    }

    private void transferFromCreditCard(CreditCardDTO creditCard, double amount) {
    }

    private void withdrawToCreditCard(CreditCardDTO creditCard, double amount) {
    }

}
