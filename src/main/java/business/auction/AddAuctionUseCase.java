package main.java.business.auction;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.entities.Auction;
import org.apache.logging.log4j.core.time.Instant;

import java.time.LocalDateTime;
import java.util.Date;

public class AddAuctionUseCase {
    IAuctionGateway auctionGateway;

    public AddAuctionUseCase() {
        auctionGateway = new AuctionRepository();
    }

    public CosmosItemResponse<AuctionDAO> addAuction(Auction auction) {
        AuctionDAO auctionDAO = new AuctionDAO(auction);
        auctionDAO.setEndTime(LocalDateTime.now().plusHours(2).toString());
        return auctionGateway.putAuction(auctionDAO);
    }
}
