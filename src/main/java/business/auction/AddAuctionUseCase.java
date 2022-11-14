package main.java.business.auction;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.entities.Auction;

import java.time.LocalDateTime;
import java.util.Date;

public class AddAuctionUseCase {

    public static CosmosItemResponse<AuctionDAO> addAuction(Auction auction) {
		IAuctionGateway auctionGateway = new AuctionRepository();
        AuctionDAO auctionDAO = new AuctionDAO(auction);
        Date dt = new Date();
        LocalDateTime.from(dt.toInstant()).plusDays(1);
        auctionDAO.setEndTime(dt);
        return auctionGateway.putAuction(auctionDAO);
    }
}
