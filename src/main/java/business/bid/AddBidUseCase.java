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

	public static CosmosItemResponse<BidDAO> addBid(Bid bid, String auctionID) {
		IBidGateway bidGateway = new BidRepository();
        IAuctionGateway auctionGateway = new AuctionRepository();
		
        BidDAO bidDAO = new BidDAO(bid);
        AuctionDAO auction = auctionGateway.getAuctionById(auctionID).stream().findFirst().get();
        Date endtime = auction.getEndTime();
        Date now = new Date(System.currentTimeMillis());
        if(endtime.before(now)) {

        } else {
            return bidGateway.addBid(bidDAO, auctionID);

        }
        return bidGateway.addBid(bidDAO, auctionID);
    }
}
