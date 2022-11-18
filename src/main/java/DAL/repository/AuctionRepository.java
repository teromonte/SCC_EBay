package main.java.DAL.repository;

import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import jakarta.ws.rs.core.Response;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.models.DAO.AuctionDAO;
import main.java.utils.GenericExceptionMapper;
import redis.clients.jedis.Jedis;

import java.util.NoSuchElementException;

public class AuctionRepository implements IAuctionGateway {

    private CosmosDBLayer auctions;

    public AuctionRepository() {
        auctions = getDBLayer();
    }

    private CosmosDBLayer getDBLayer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.AUCTION_CONTAINER);
        return db;
    }

    @Override
    public Response putAuction(AuctionDAO auction) {
        AuctionDAO res;
        GenericExceptionMapper g = new GenericExceptionMapper();
        try {
            var u = getAuctionById(auction.getId()).stream().findFirst().get();
            PartitionKey key = new PartitionKey(auction.getId());
            auction.setId(u.getId());
            auctions = getDBLayer();
            res = auctions.getContainer().replaceItem(auction, u.getId(), key, new CosmosItemRequestOptions()).getItem();
        } catch (NoSuchElementException e) {
            auctions = getDBLayer();
            res = auctions.getContainer().createItem(auction).getItem();

        } catch (Exception e) {
            auctions.close();


            return g.toResponse(e);

        }
        auctions.close();

        if (res != null) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                ObjectMapper mapper = new ObjectMapper();
                jedis.set("auction:" + auction.getId(), mapper.writeValueAsString(auction));
            } catch (Exception e) {
                return g.toResponse(e);
            }
        }
        return Response.ok(res).build();


    }

    @Override
    public CosmosPagedIterable<AuctionDAO> getAuctionById(String id) {
        var res = auctions.getContainer().queryItems("SELECT * FROM auctions WHERE auctions.id=\"" + id + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
        auctions.close();
        return res;
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose() {
        CosmosPagedIterable<AuctionDAO> pi = auctions.getContainer().queryItems("SELECT * FROM auctions", new CosmosQueryRequestOptions(), AuctionDAO.class);
        auctions.close();
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
        var res = auctions.getContainer().queryItems("SELECT * FROM auctions WHERE auctions.owner=\"" + userID + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
        auctions.close();
        return res;
    }
}

