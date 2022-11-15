package main.java.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Bid {
    private String id; // code
    private String auction; // code
    private String user;
    private String value;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date time;

    public Bid() {
        super();
    }

    public Bid(String id, String auction, String user, String value, Date time) {
        this.id = id;
        this.auction = auction;
        this.user = user;
        this.value = value;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Bid{" + "id='" + id + '\'' + ", auction='" + auction + '\'' + ", user='" + user + '\'' + ", value='" + value + '\'' + ", time=" + time + '}';
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuction() {
        return auction;
    }

    public void setAuction(String auction) {
        this.auction = auction;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
