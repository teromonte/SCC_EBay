package main.java.business.auction;

import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

public class ListAuctionsFromUserUseCase {

    public static CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID) {
		IAuctionGateway auctionGateway = new AuctionRepository();
        return auctionGateway.listAuctionsFromUser(userID);
    }
}