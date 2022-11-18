package main.java.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Bid {
    private String auction;
    private String user;
    private float value;

    public Bid() {
        super();
    }

    public Bid(String auctionId, String user, float value) {
        this.auction = auctionId;
        this.user = user;
        this.value = value;
    }

    public String getAuction() {
        return auction;
    }

    public void setAuction(String auctionId) {
        this.auction = auctionId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
