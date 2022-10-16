package main.java.srv.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.business.auction.AddAuctionUseCase;
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
        AddAuctionUseCase auctionUseCase = new AddAuctionUseCase();
        return auctionUseCase.addAuction(auction).getItem().toString();
    }

    @Path("/{auctionID}/bid")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addBid(Bid bid, @PathParam("auctionID") String auctionID) {
        AddBidUseCase bidUseCase = new AddBidUseCase();
        return bidUseCase.addBid(bid, auctionID).getItem().toString();
    }

    @Path("/{auctionID}/bid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listBid(@PathParam("auctionID") String auctionID) {
        ListBidsUseCase listBidsUseCase = new ListBidsUseCase();
        return listBidsUseCase.listBids(auctionID).stream().toList().toString();
    }

    @Path("/{auctionID}/question")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuestion(@PathParam("auctionID") String auctionID) {
        ListQuestionsUseCase listQuestionsUseCase = new ListQuestionsUseCase();
        return listQuestionsUseCase.listQuestions(auctionID).stream().toList().toString();
    }

    @Path("/{auctionID}/question")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addQuestion(Question question, @PathParam("auctionID") String auctionID) {
        AddQuestionUseCase addQuestionUseCase = new AddQuestionUseCase();
        return addQuestionUseCase.addQuestion(question, auctionID).getItem().toString();
    }

}
