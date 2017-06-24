package com.example.demo.services;

import com.example.demo.domain.CreditCardDTO;
import com.example.demo.domain.TransactionDTO;
import com.example.demo.exceptions.UserException;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
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
