package main.java.DAL.gateway;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.models.DAO.AuctionDAO;

public interface IAuctionGateway {
    public CosmosItemResponse<AuctionDAO> putAuction(AuctionDAO auction);

    public CosmosPagedIterable<AuctionDAO> getAuctionById(String id);

    public CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose();

    public CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID);
}
