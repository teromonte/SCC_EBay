package main.java.models.DAO;

import main.java.models.entities.Bid;

public class BidDAO {
    private String _rid;
    private String _ts;
    private String id;
    private String auction;
    private String user;
    private String value;

    public BidDAO(String id, String auction, String user, String value) {
        super();
        this.id = id;
        this.auction = auction;
        this.user = user;
        this.value = value;
    }

    public BidDAO(Bid bid) {
        this(bid.getId(), bid.getAuction(), bid.getUser(), bid.getValue());
    }

    public BidDAO() {

    }

    public Bid toBid() {
        return new Bid(id, auction, user, value);
    }

    @Override
    public String toString() {
        return "BidDAO{" + "_rid='" + _rid + '\'' + ", _ts='" + _ts + '\'' + ", id='" + id + '\'' + ", auction='" + auction + '\'' + ", user='" + user + '\'' + ", value='" + value + '\'' + '}';
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
