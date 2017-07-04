package com.webserver.domain;

import java.util.Date;

/**
 * Created by Marin on 1/12/2017.
 */
public class PriceDTO {

    private Date date;
    private Double buyPrice;
    private Double sellPrice;

    public PriceDTO() {
    }

    public PriceDTO(Date date, Double buyPrice, double sellPrice) {
        this.date = date;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public static class Builder {

        private Date nestedDate;
        private Double nestedBuyPrice;
        private Double nestedSellPrice;

        public Builder date(Date date) {
            this.nestedDate = date;
            return this;
        }

        public Builder buyPrice(Double buyPrice) {
            this.nestedBuyPrice = buyPrice;
            return this;
        }
        public Builder sellPrice(Double sellPrice) {
            this.nestedSellPrice = sellPrice;
            return this;
        }

        public PriceDTO create() {
            return new PriceDTO(nestedDate, nestedBuyPrice, nestedSellPrice);
        }

    }
}
