package main.java.DAL.gateway;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.models.DAO.BidDAO;
import main.java.models.entities.Bid;

public interface IBidGateway {
    public CosmosPagedIterable<BidDAO> listBids(String auctionID);

    public BidDAO addBid(BidDAO bid, String auctionID);
}
