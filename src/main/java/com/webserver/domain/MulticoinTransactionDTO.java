package com.webserver.domain;

/**
 * Created by Marin on 6/26/2017.
 */
public class MulticoinTransactionDTO {

    private int userId;
    private double amount;

    public MulticoinTransactionDTO(int userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public MulticoinTransactionDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MulticoinTransactionDTO{" +
                "userId='" + userId + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MulticoinTransactionDTO that = (MulticoinTransactionDTO) o;

        if (userId != that.userId) return false;
        return Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
