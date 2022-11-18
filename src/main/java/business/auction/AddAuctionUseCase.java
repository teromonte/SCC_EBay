package main.java.business.auction;

import jakarta.ws.rs.core.Response;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.entities.Auction;

public class AddAuctionUseCase {

    public static Response addAuction(Auction auction) {
        IAuctionGateway auctionGateway = new AuctionRepository();
        AuctionDAO auctionDAO = new AuctionDAO(auction);
        return auctionGateway.putAuction(auctionDAO);
    }
}
