package com.webserver.models;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Marin on 1/12/2017.
 */
@Entity
@Table(name = "price")
public class Price {

    private Integer id;
    private Date date;
    private Double buyPrice;
    private Double sellPrice;

    public Price() {
    }

    public Price(Integer id, Date date, Double buyPrice, Double sellPrice) {
        this.id = id;
        this.date = date;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "buyPrice")
    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Column(name = "sellPrice")
    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", date=" + date +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (id != null ? !id.equals(price.id) : price.id != null) return false;
        if (date != null ? !date.equals(price.date) : price.date != null) return false;
        if (buyPrice != null ? !buyPrice.equals(price.buyPrice) : price.buyPrice != null) return false;
        return sellPrice != null ? sellPrice.equals(price.sellPrice) : price.sellPrice == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (buyPrice != null ? buyPrice.hashCode() : 0);
        result = 31 * result + (sellPrice != null ? sellPrice.hashCode() : 0);
        return result;
    }
}
