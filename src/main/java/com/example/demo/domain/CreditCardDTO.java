package com.example.demo.domain;

/**
 * Created by Marin on 6/24/2017.
 */
public class CreditCardDTO {

    private String cardId, cardOwner;
    private int expMonth, expYear, cct;

    public CreditCardDTO(String cardId, String cardOwner, int expMonth, int expYear, int cct) {
        this.cardId = cardId;
        this.cardOwner = cardOwner;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cct = cct;
    }

    public CreditCardDTO() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public int getCct() {
        return cct;
    }

    public void setCct(int cct) {
        this.cct = cct;
    }

    @Override
    public String toString() {
        return "CreditCardDTO{" +
                "cardId='" + cardId + '\'' +
                ", cardOwner='" + cardOwner + '\'' +
                ", expMonth=" + expMonth +
                ", expYear=" + expYear +
                ", cct=" + cct +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreditCardDTO that = (CreditCardDTO) o;

        if (expMonth != that.expMonth) return false;
        if (expYear != that.expYear) return false;
        if (cct != that.cct) return false;
        if (cardId != null ? !cardId.equals(that.cardId) : that.cardId != null) return false;
        return cardOwner != null ? cardOwner.equals(that.cardOwner) : that.cardOwner == null;
    }

    @Override
    public int hashCode() {
        int result = cardId != null ? cardId.hashCode() : 0;
        result = 31 * result + (cardOwner != null ? cardOwner.hashCode() : 0);
        result = 31 * result + expMonth;
        result = 31 * result + expYear;
        result = 31 * result + cct;
        return result;
    }
}
