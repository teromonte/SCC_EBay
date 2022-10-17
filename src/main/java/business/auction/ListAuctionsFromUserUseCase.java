package main.java.business.auction;

import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

public class ListAuctionsFromUserUseCase {
    IAuctionGateway auctionGateway;

    public ListAuctionsFromUserUseCase() {
        auctionGateway = new AuctionRepository();
    }

    public CosmosPagedIterable<AuctionDAO> ListAuctionsFromUser(String userID) {
        return auctionGateway.listAuctionsFromUser(userID);
    }
}