package main.java.business.bid;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IBidGateway;
import main.java.DAL.repository.BidRepository;
import main.java.models.DAO.BidDAO;
import main.java.models.entities.Bid;

public class AddBidUseCase {
    IBidGateway bidGateway;

    public AddBidUseCase() {
        bidGateway = new BidRepository();
    }

    public CosmosItemResponse<BidDAO> addBid(Bid bid) {
        BidDAO bidDAO = new BidDAO(bid);
        return bidGateway.addBid(bidDAO);
    }
}
