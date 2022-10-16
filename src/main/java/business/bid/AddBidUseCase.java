package main.java.business.bid;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.gateway.IBidGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.repository.BidRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.DAO.BidDAO;
import main.java.models.entities.Bid;

import java.time.LocalDateTime;
import java.util.Date;

public class AddBidUseCase {
    IBidGateway bidGateway;

    IAuctionGateway auctionGateway;


    public AddBidUseCase() {
        bidGateway = new BidRepository();
        auctionGateway = new AuctionRepository();

    }

    public CosmosItemResponse<BidDAO> addBid(Bid bid, String auctionID) {
        BidDAO bidDAO = new BidDAO(bid);
        AuctionDAO auction = auctionGateway.getAuctionById(auctionID).stream().findFirst().get();
        LocalDateTime endtime = LocalDateTime.parse(auction.getEndTime());
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(endtime)) {

        } else {
            return bidGateway.addBid(bidDAO, auctionID);

        }
        return bidGateway.addBid(bidDAO, auctionID);
    }
}
