package main.java.DAL.gateway;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.core.Response;
import main.java.models.DAO.AuctionDAO;

public interface IAuctionGateway {
    public Response putAuction(AuctionDAO auction);

    public AuctionDAO getAuctionById(String id) throws JsonProcessingException;

    public CosmosPagedIterable<AuctionDAO> listAllAuctionsAboutToClose();

    public CosmosPagedIterable<AuctionDAO> listAllAuctionsOpen();

    public CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID);
}
