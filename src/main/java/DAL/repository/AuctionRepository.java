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
    //        PartitionKey key = new PartitionKey(id);
    public AuctionRepository() {
    }

    private CosmosContainer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.AUCTION_CONTAINER);
        return db.getContainer();
    }

    @Override
    public CosmosItemResponse<AuctionDAO> putAuction(AuctionDAO auction) {
        CosmosContainer auctions = getContainer();
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
        CosmosContainer auctions = getContainer();
        return auctions.queryItems("SELECT * FROM auction WHERE auctions.id=\"" + id + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
    }
}

