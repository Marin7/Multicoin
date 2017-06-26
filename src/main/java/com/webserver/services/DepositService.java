package com.webserver.services;

import com.webserver.domain.CreditCardTransactionDTO;
import com.webserver.exceptions.UserException;
import com.webserver.models.User;
import com.webserver.repositories.UserRepository;
import com.webserver.transactions.TransferManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Marin on 6/26/2017.
 */
@Service
public class DepositService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransferManager transferManager;

    public void depositMoney(CreditCardTransactionDTO creditCardTransactionDTO) {
        User user = userRepository.findById(creditCardTransactionDTO.getUserId());
        if (user == null) {
            throw new UserException("No user with id: " + creditCardTransactionDTO.getUserId());
        }
        transferManager.transferMoneyFromCreditCard(creditCardTransactionDTO.getCard(), creditCardTransactionDTO.getAmount());
        user.setDollars(user.getDollars() + creditCardTransactionDTO.getAmount());
        userRepository.save(user);
    }

}
