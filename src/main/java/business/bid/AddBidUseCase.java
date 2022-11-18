package main.java.business.bid;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.gateway.IBidGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.repository.BidRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.DAO.BidDAO;
import main.java.models.entities.Bid;
import main.java.utils.GenericExceptionMapper;

public class AddBidUseCase {

    public static Response addBid(Bid bid, String auctionID) {
        GenericExceptionMapper g = new GenericExceptionMapper();
        IBidGateway bidGateway = new BidRepository();
        IAuctionGateway auctionGateway = new AuctionRepository();
        BidDAO bidDAO = new BidDAO(bid);

        try {
            AuctionDAO auction = auctionGateway.getAuctionById(auctionID).stream().findFirst().get();
            if (auction.getStatus().equals("OPEN")) {
                var res = bidGateway.addBid(bidDAO, auctionID);
                return Response.ok(res).build();
            } else {
                throw new InternalServerErrorException("Auction closed");
            }
        } catch (Exception e) {
            return g.toResponse(e);
        }


    }
}
