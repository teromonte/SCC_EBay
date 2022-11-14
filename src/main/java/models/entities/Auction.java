package main.java.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Auction {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(float minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public Bid getWinnerBid() {
        return winnerBid;
    }

    public void setWinnerBid(Bid winnerBid) {
        this.winnerBid = winnerBid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String id; // code
    private String title;
    private String description;
    private String image;
    private String owner;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date endTime;
    private float minimumPrice;
    private Bid winnerBid;
    private String status; // code

    public Auction() {
        super();
    }

    public Auction(String id, String title, String description, String image, String owner, Date endTime, float minimumPrice, Bid winnerBid, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.endTime = endTime;
        this.minimumPrice = minimumPrice;
        this.winnerBid = winnerBid;
        this.status = status;
    }

}
