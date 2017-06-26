package com.webserver.domain;

/**
 * Created by Marin on 6/24/2017.
 */
public class CreditCardTransactionDTO {

    private CreditCardDTO card;
    private double amount;
    private int userId;

    public CreditCardTransactionDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CreditCardDTO getCard() {
        return card;
    }

    public void setCard(CreditCardDTO card) {
        this.card = card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CreditCardTransactionDTO{" +
                "card=" + card +
                ", userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
