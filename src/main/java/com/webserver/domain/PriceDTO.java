package com.webserver.domain;

import java.util.Date;

/**
 * Created by Marin on 1/12/2017.
 */
public class PriceDTO {

    private Integer id;
    private Date date;
    private Double buyPrice;
    private Double sellPrice;

    public PriceDTO() {
    }

    public PriceDTO(Integer id, Date date, Double buyPrice, double sellPrice) {
        this.id = id;
        this.date = date;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        private Integer nestedId;
        private Date nestedDate;
        private Double nestedBuyPrice;
        private Double nestedSellPrice;

        public Builder id(Integer id) {
            this.nestedId = id;
            return this;
        }

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
            return new PriceDTO(nestedId, nestedDate, nestedBuyPrice, nestedSellPrice);
        }

    }
}
