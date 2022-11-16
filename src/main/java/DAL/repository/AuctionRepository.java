package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.models.DAO.AuctionDAO;
import redis.clients.jedis.Jedis;

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
        CosmosItemResponse<AuctionDAO> res;
        if (u == null) {
            String id = "0:" + System.currentTimeMillis();
            auction.setId(id);
            res = auctions.createItem(auction);
        } else {
            PartitionKey key = new PartitionKey(auction.getId());
            res = auctions.replaceItem(auction, auction.getId(), key, new CosmosItemRequestOptions());
        }
        if (res.getStatusCode() < 300) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                ObjectMapper mapper = new ObjectMapper();
                jedis.set("auction:" + auction.getId(), mapper.writeValueAsString(auction));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> getAuctionById(String id) {
        return auctions.queryItems("SELECT * FROM auctions WHERE auctions.id=\"" + id + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose() {
        CosmosPagedIterable<AuctionDAO> pi = auctions.queryItems("SELECT * FROM auctions", new CosmosQueryRequestOptions(), AuctionDAO.class);
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            ObjectMapper mapper = new ObjectMapper();
            for (AuctionDAO item : pi)
                jedis.rpush("auctionL", mapper.writeValueAsString(item));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID) {
        return auctions.queryItems("SELECT * FROM auctions WHERE auctions.owner=\"" + userID + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
    }
}

