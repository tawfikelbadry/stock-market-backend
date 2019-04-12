package com.gradteam.porsaty.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tawfik on 3/20/2018.
 */

@Entity
public class TradingOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double wantedPrice;
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    private int count;

    private boolean isAccepted=false;

    @ManyToOne
    private NormalUser user;

    @ManyToOne
    private Stock stock;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getWantedPrice() {
        return wantedPrice;
    }

    public void setWantedPrice(double wantedPrice) {
        this.wantedPrice = wantedPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public NormalUser getUser() {
        return user;
    }

    public void setUser(NormalUser user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
