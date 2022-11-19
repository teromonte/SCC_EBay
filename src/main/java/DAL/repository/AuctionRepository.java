package main.java.DAL.repository;

import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.models.DAO.AuctionDAO;
import main.java.utils.GenericExceptionMapper;
import redis.clients.jedis.Jedis;

import java.util.Random;

import static main.java.srv.MainApplication.CACHE_FLAG;

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

        if (auction.getId() == null) {
            Random rand = new Random();
            auction.setId(rand.nextInt(9999) + ":" + auction.getOwner());
            auctions = getDBLayer();
            res = auctions.getContainer().createItem(auction).getItem();
            auctions.close();
        } else {
            try {
                PartitionKey key = new PartitionKey(auction.getId());
                auction.setId(auction.getId());
                auctions = getDBLayer();
                res = auctions.getContainer().replaceItem(auction, auction.getId(), key, new CosmosItemRequestOptions()).getItem();
                auctions.close();
            } catch (Exception e) {
                auctions.close();
                return g.toResponse(e);
            }
        }

        if (res != null && CACHE_FLAG) {
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
    public AuctionDAO getAuctionById(String id) throws JsonProcessingException {
        AuctionDAO a = null;
        if (CACHE_FLAG) a = CachePlus.getAuction(id);
        if (a == null)
            a = auctions.getContainer().queryItems("SELECT * FROM auctions WHERE auctions.id=\"" + id + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class).stream().findFirst().get();
        if (a == null) throw new NotFoundException();
        auctions.close();
        return a;
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAllAuctionsAboutToClose() {
        CosmosPagedIterable<AuctionDAO> pi = auctions.getContainer().queryItems("SELECT TOP 20 * FROM auctions where auctions.status=\"OPEN\" order by auctions.endTime asc", new CosmosQueryRequestOptions(), AuctionDAO.class);
        auctions.close();
        if (CACHE_FLAG) CachePlus.cacheThenCPI(pi, null, CachePlus.AUCTION_LIST);
        return pi;
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAllAuctionsOpen() {
        CosmosPagedIterable<AuctionDAO> pi = auctions.getContainer().queryItems("SELECT * FROM auctions where auctions.status=\"" + "OPEN" + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
        auctions.close();
        CachePlus.cacheThenCPI(pi, null, CachePlus.AUCTION_LIST);
        return pi;
    }

    @Override
    public CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID) {
        CosmosPagedIterable<AuctionDAO> pi = auctions.getContainer().queryItems("SELECT * FROM auctions WHERE auctions.owner=\"" + userID + "\"", new CosmosQueryRequestOptions(), AuctionDAO.class);
        auctions.close();
        if (CACHE_FLAG) CachePlus.cacheThenCPI(pi, userID, CachePlus.USER_AUCTIONS);
        return pi;
    }
}

