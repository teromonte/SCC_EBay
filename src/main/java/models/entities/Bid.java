package main.java.models.entities;

public class Bid {
    private String id; // code
    private String auction; // code
    private String user;
    private String value;

    public Bid() {
        super();
    }

    public Bid(String id, String auction, String user, String value) {
        this.id = id;
        this.auction = auction;
        this.user = user;
        this.value = value;
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

    @Override
    public String toString() {
        return "Bid{" + "id='" + id + '\'' + ", auction='" + auction + '\'' + ", user='" + user + '\'' + ", value='" + value + '\'' + '}';
    }
}
