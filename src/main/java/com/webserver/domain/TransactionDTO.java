package com.webserver.domain;

/**
 * Created by Marin on 6/24/2017.
 */
public class TransactionDTO {

    private CreditCardDTO card;
    private double amount;
    private int userId;

    public TransactionDTO(CreditCardDTO card, double amount, int userId) {
        this.card = card;
        this.amount = amount;
        this.userId = userId;
    }

    public TransactionDTO() {
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
        return "TransactionDTO{" +
                "card=" + card +
                ", userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
