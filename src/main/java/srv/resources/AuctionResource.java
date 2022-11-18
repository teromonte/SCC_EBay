package main.java.srv.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.business.auction.AddAuctionUseCase;
import main.java.business.auction.ListAuctionsAboutToCloseUseCase;
import main.java.business.auction.ListAuctionsFromUserUseCase;
import main.java.business.bid.AddBidUseCase;
import main.java.business.bid.ListBidsUseCase;
import main.java.business.question.AddQuestionUseCase;
import main.java.business.question.ListQuestionsUseCase;
import main.java.business.session.CheckCookieUseCase;
import main.java.models.entities.Auction;
import main.java.models.entities.Bid;
import main.java.models.entities.Question;
import main.java.models.entities.Session;
import main.java.utils.GenericExceptionMapper;

import java.util.List;

/**
 * Class with auction endpoints.
 */
@Path("/auction")
public class AuctionResource {
    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAuction(@CookieParam("scc:session") Cookie session, Auction auction) {
        try {
            Session s = CheckCookieUseCase.checkCookieUser(session, auction.getOwner());
            auction.setOwner(s.getUser());
            return AddAuctionUseCase.addAuction(auction);
        } catch (Exception e) {
            GenericExceptionMapper c = new GenericExceptionMapper();
            return c.toResponse(e);
        }

    }

    @Path("/listAuctionsAboutToClose")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listAuctionsAboutToClose() {
        List<String> res = ListAuctionsAboutToCloseUseCase.cacheListAuctionsAboutToClose();
        if (res != null) return res.toString();
        return ListAuctionsAboutToCloseUseCase.listAuctionsAboutToClose().stream().toList().toString();
    }

    @Path("/listAuctionsFromUser/{userID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listAuctionsFromUser(@PathParam("userID") String userID) {
        return ListAuctionsFromUserUseCase.listAuctionsFromUser(userID).stream().toList().toString();
    }

    @Path("/{auctionID}/bid")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addBid(Bid bid, @PathParam("auctionID") String auctionID) {
        return AddBidUseCase.addBid(bid, auctionID).getItem().toString();
    }

    @Path("/{auctionID}/bid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listBid(@PathParam("auctionID") String auctionID) {
        List<String> res = ListBidsUseCase.cacheListBids(auctionID);
        if (res != null) return res.toString();
        return ListBidsUseCase.listBids(auctionID).stream().toList().toString();
    }

    @Path("/{auctionID}/question")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuestion(@PathParam("auctionID") String auctionID) {
        List<String> res = ListQuestionsUseCase.cacheListQuestions(auctionID);
        if (res != null) return res.toString();
        return ListQuestionsUseCase.listQuestions(auctionID).stream().toList().toString();
    }

    @Path("/{auctionID}/question")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addQuestion(Question question, @PathParam("auctionID") String auctionID) {
        return AddQuestionUseCase.addQuestion(question, auctionID).getItem().toString();
    }

}
