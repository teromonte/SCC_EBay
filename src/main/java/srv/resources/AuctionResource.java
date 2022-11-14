package main.java.srv.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.business.auction.AddAuctionUseCase;
import main.java.business.auction.ListAuctionsAboutToCloseUseCase;
import main.java.business.auction.ListAuctionsFromUserUseCase;
import main.java.business.bid.AddBidUseCase;
import main.java.business.bid.ListBidsUseCase;
import main.java.business.question.AddQuestionUseCase;
import main.java.business.question.ListQuestionsUseCase;
import main.java.models.entities.Auction;
import main.java.models.entities.Bid;
import main.java.models.entities.Question;

/**
 * Class with auction endpoints.
 */
@Path("/auction")
public class AuctionResource {
    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addAuction(Auction auction) {
        return AddAuctionUseCase
		.addAuction(auction)
		.getItem().toString();
    }

    @Path("/listAuctionsAboutToClose")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listAuctionsAboutToClose() {
        return ListAuctionsAboutToCloseUseCase
		.listAuctionsAboutToClose()
		.stream().toList().toString();
    }

    @Path("/listAuctionsFromUser/{userID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listAuctionsFromUser(@PathParam("userID") String userID) {
        return ListAuctionsFromUserUseCase
		.listAuctionsFromUser(userID)
		.stream().toList().toString();
    }

    @Path("/{auctionID}/bid")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addBid(Bid bid, @PathParam("auctionID") String auctionID) {
        return AddBidUseCase
		.addBid(bid, auctionID)
		.getItem().toString();
    }

    @Path("/{auctionID}/bid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listBid(@PathParam("auctionID") String auctionID) {
        return ListBidsUseCase
		.listBids(auctionID)
		.stream().toList().toString();
    }

    @Path("/{auctionID}/question")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuestion(@PathParam("auctionID") String auctionID) {
        return ListQuestionsUseCase
		.listQuestions(auctionID)
		.stream().toList().toString();
    }

    @Path("/{auctionID}/question")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addQuestion(Question question, @PathParam("auctionID") String auctionID) {
        return AddQuestionUseCase
		.addQuestion(question, auctionID)
		.getItem().toString();
    }

}
