package main.java.srv.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.business.auction.AddAuctionUseCase;
import main.java.business.auction.ListAuctionsAboutToCloseUseCase;
import main.java.business.bid.AddBidUseCase;
import main.java.business.bid.ListBidsUseCase;
import main.java.business.question.AddQuestionUseCase;
import main.java.business.question.AddReplyUseCase;
import main.java.business.question.ListQuestionsUseCase;
import main.java.business.session.CheckCookieUseCase;
import main.java.models.entities.*;
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
            return AddAuctionUseCase.addAuction(auction);
        } catch (Exception e) {
            return Response.accepted(e).build();
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

    @Path("/{auctionID}/bid")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBid(@CookieParam("scc:session") Cookie session, Bid bid, @PathParam("auctionID") String auctionID) {
        try {
            Session s = CheckCookieUseCase.checkCookieUser(session, bid.getUser());
            return AddBidUseCase.addBid(bid, auctionID);
        } catch (Exception e) {
            GenericExceptionMapper g = new GenericExceptionMapper();
            return g.toResponse(e);
        }
    }

    @Path("/{auctionID}/bid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listBid(@PathParam("auctionID") String auctionID) {
        try {
            return ListBidsUseCase.cacheListBids(auctionID).toString();

        } catch (Exception e) {
            return ListBidsUseCase.listBids(auctionID).stream().toList().toString();
        }
    }

    @Path("/{auctionID}/question")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listQuestion(@PathParam("auctionID") String auctionID) {
        try {
            return ListQuestionsUseCase.cacheListQuestions(auctionID).toString();
        } catch (Exception e) {
            return ListQuestionsUseCase.listQuestions(auctionID).stream().toList().toString();
        }
    }

    @Path("/{auctionID}/question")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addQuestion(@CookieParam("scc:session") Cookie session, Question question, @PathParam("auctionID") String auctionID) {
        try {
            Session s = CheckCookieUseCase.checkCookieUser(session, question.getUser());
            return AddQuestionUseCase.addQuestion(question, auctionID);
        } catch (Exception e) {
            GenericExceptionMapper g = new GenericExceptionMapper();
            return g.toResponse(e);
        }
    }

    @Path("/{auctionID}/question/{questionID}/reply")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReply(@CookieParam("scc:session") Cookie session, Reply reply, @PathParam("auctionID") String auctionID, @PathParam("questionID") String questionID) {
        try {
            Session s = CheckCookieUseCase.checkCookie(session);
            return AddReplyUseCase.addReply(reply, auctionID, questionID, s.getUser());
        } catch (Exception e) {
            GenericExceptionMapper g = new GenericExceptionMapper();
            return g.toResponse(e);
        }
    }

}
