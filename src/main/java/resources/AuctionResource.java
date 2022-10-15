package main.java.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    public String addAuction(Auction auction) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(auction);
    }

    @Path("/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateAuction(Auction auction, @PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(auction);
    }

    @Path("/{id}/bid")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addBid(Bid bid, @PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bid);
    }

    @Path("/{id}/bid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listBid(@PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //return mapper.writeValueAsString(bid);
        return null;
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
