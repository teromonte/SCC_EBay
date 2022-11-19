package main.java.DAL.repository;

import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IBidGateway;
import main.java.models.DAO.BidDAO;
import redis.clients.jedis.Jedis;

import java.util.Date;

import static main.java.srv.MainApplication.CACHE_FLAG;

public class BidRepository implements IBidGateway {

    private CosmosDBLayer bids;

    public BidRepository() {
        this.bids = getContainer();
    }

    private CosmosDBLayer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.BID_CONTAINER);
        return db;
    }


    @Override
    public CosmosPagedIterable<BidDAO> listBids(String auctionID) {
        CosmosPagedIterable<BidDAO> pi = bids.getContainer().queryItems("SELECT * FROM bids WHERE bids.auction=\"" + auctionID + "\"", new CosmosQueryRequestOptions(), BidDAO.class);
        bids.close();
        if (CACHE_FLAG) CachePlus.cacheThenCPI(pi, auctionID, CachePlus.BID_LIST);
        return pi;

    }

    @Override
    public BidDAO addBid(BidDAO bidDAO, String auctionID) {
        String id = "Bid:" + System.currentTimeMillis();
        bidDAO.setId(id);

        bidDAO.setTime(new Date());

        BidDAO res = bids.getContainer().createItem(bidDAO).getItem();
        bids.close();
        if (res != null && CACHE_FLAG) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                ObjectMapper mapper = new ObjectMapper();
                jedis.set("bid:" + res.getId(), mapper.writeValueAsString(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
