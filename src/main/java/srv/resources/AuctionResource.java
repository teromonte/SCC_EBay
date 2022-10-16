package main.java.srv.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.business.auction.AddAuctionUseCase;
import main.java.business.bid.AddBidUseCase;
import main.java.business.bid.ListBidsUseCase;
import main.java.models.entities.Auction;
import main.java.models.entities.Bid;

/**
 * Class with control endpoints.
 */
@Path("/auction")
public class AuctionResource {
    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addAuction(Auction auction) {
        AddAuctionUseCase auctionUseCase = new AddAuctionUseCase();
        return auctionUseCase.addAuction(auction).getItem().toString();
    }

    @Path("/{id}/bid")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addBid(Bid bid) {
        AddBidUseCase bidUseCase = new AddBidUseCase();
        return bidUseCase.addBid(bid).getItem().toString();
    }

    @Path("/{id}/bid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listBid(@PathParam("id") String id)  {
        ListBidsUseCase listBidsUseCase = new ListBidsUseCase();
        return listBidsUseCase.listBids(id).stream().toList().toString();
    }

    @Path("/{id}/question")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuestion(@PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //return mapper.writeValueAsString(bid);
        return null;
    }

    @Path("/{id}/question")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addQuestion(Bid bid, @PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bid);
    }

    @Path("/{id}/question/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String replyQuestion(Bid bid, @PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bid);
    }


}
