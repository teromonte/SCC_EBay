package main.java.models.entities;

public class Auction {
    private String id;
    private String title;
    private String description;
    private String image;
    private String owner;
    private String endTime;
    private String minimumPrice;
    private String winnerBid;
    private String status;

    public Auction(String id, String title, String description, String image, String owner, String endTime, String minimumPrice, String winnerBid, String status) {
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

    @Override
    public String toString() {
        return "Auction{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description + '\'' + ", image='" + image + '\'' + ", owner='" + owner + '\'' + ", endTime='" + endTime + '\'' + ", minimumPrice='" + minimumPrice + '\'' + ", winnerBid='" + winnerBid + '\'' + ", status='" + status + '\'' + '}';
    }

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getWinnerBid() {
        return winnerBid;
    }

    public void setWinnerBid(String winnerBid) {
        this.winnerBid = winnerBid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
