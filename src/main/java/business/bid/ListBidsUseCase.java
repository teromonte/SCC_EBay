package main.java.business.bid;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IBidGateway;
import main.java.DAL.repository.BidRepository;
import main.java.models.DAO.BidDAO;
import main.java.models.entities.Bid;

public class ListBidsUseCase {
    IBidGateway bidGateway;
    public ListBidsUseCase() {
        bidGateway = new BidRepository();
    }
    public CosmosPagedIterable<BidDAO> listBids(String auctionID) {
        return bidGateway.listBids(auctionID);
    }
}
