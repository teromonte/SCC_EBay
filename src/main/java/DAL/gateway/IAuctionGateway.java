package main.java.DAL.gateway;

import main.java.models.entities.Bid;

public interface IAuctionGateway {
    public void placeBid(Bid bid);
}
