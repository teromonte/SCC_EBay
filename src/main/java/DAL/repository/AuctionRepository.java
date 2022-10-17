package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.models.DAO.AuctionDAO;

public class AuctionRepository implements IAuctionGateway {

    private CosmosContainer auctions;

    public AuctionRepository() {
        auctions = getContainer();
    }

    private CosmosContainer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.AUCTION_CONTAINER);
        return db.getContainer();
    }

    @Override
    public CosmosItemResponse<AuctionDAO> putAuction(AuctionDAO auction) {
        var u = getAuctionById(auction.getId());
        if (u == null) {
            String id = "0:" + System.currentTimeMillis();
            auction.setId(id);
            return auctions.createItem(auction);
        } else {
            PartitionKey key = new PartitionKey(auction.getId());
            return auctions.replaceItem(auction, auction.getId(), key, new CosmosItemRequestOptions());
        }
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> getAuctionById(String id) {
        return auctions.queryItems("SELECT * FROM auctions WHERE auctions.id=\"" + id + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose() {
        return auctions.queryItems("SELECT * FROM auctions", new CosmosQueryRequestOptions(), AuctionDAO.class);
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID) {
        return auctions.queryItems("SELECT * FROM auctions WHERE auctions.owner=\"" + userID + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
    }
}

