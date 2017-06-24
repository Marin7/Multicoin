package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Marin on 1/12/2017.
 */
@Entity
@Table(name = "price")
public class Price {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Date date;
    private Double dollars;

    public Price() {
    }

    public Price(Integer id, Date date, Double dollars) {
        this.id = id;
        this.date = date;
        this.dollars = dollars;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "dollars", nullable = false)
    public Double getDollars() {
        return dollars;
    }

    public void setDollars(Double dollars) {
        this.dollars = dollars;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", date=" + date +
                ", dollars=" + dollars +
                '}';
    }
}
