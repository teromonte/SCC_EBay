package main.java.business.auction;

import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

public class ListAuctionsAboutToCloseUseCase {
    IAuctionGateway auctionGateway;

    public ListAuctionsAboutToCloseUseCase() {
        auctionGateway = new AuctionRepository();
    }

    public CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose() {
        return auctionGateway.listAuctionsAboutToClose();
    }
}
