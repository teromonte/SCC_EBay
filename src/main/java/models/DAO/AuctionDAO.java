package main.java.models.DAO;

import main.java.models.entities.Auction;

public class AuctionDAO {
    private String _rid;
    private String _ts;
    private String id;
    private String title;
    private String description;
    private String image;
    private String owner;
    private String endTime;
    private String minimumPrice;
    private String winnerBid;
    private String status;

    public AuctionDAO(String id, String title, String description, String image, String owner, String endTime, String minimumPrice, String winnerBid, String status) {
        super();
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

    public AuctionDAO() {

    }

    public AuctionDAO(Auction auction) {
        this(auction.getId(), auction.getTitle(), auction.getDescription(), auction.getImage(), auction.getOwner(), auction.getEndTime(), auction.getMinimumPrice(), auction.getWinnerBid(), auction.getStatus());
    }

    public Auction toAuction() {
        return new Auction(id, title, description, image, owner, endTime, minimumPrice, winnerBid, status);
    }

    @Override
    public String toString() {
        return "AuctionDAO{" + "_rid='" + _rid + '\'' + ", _ts='" + _ts + '\'' + ", id='" + id + '\'' + ", title='" + title + '\'' + ", description='" + description + '\'' + ", image='" + image + '\'' + ", owner='" + owner + '\'' + ", endTime='" + endTime + '\'' + ", minimumPrice='" + minimumPrice + '\'' + ", winnerBid='" + winnerBid + '\'' + ", status='" + status + '\'' + '}';
    }

    public String get_rid() {
        return _rid;
    }

    public void set_rid(String _rid) {
        this._rid = _rid;
    }

    public String get_ts() {
        return _ts;
    }

    public void set_ts(String _ts) {
        this._ts = _ts;
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
