package main.java.DAL.repository;

import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.gateway.IBidGateway;
import main.java.models.DAO.BidDAO;
import redis.clients.jedis.Jedis;
import main.java.DAL.cache.CachePlus;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        return CachePlus.cacheThenCPI(pi, auctionID, CachePlus.BID_LIST);

    }

    @Override
    public BidDAO addBid(BidDAO bidDAO, String auctionID) {
        String id = "Bid:" + System.currentTimeMillis();
        bidDAO.setId(id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = new Date();

        bidDAO.setTime(date);
        BidDAO res = bids.getContainer().createItem(bidDAO).getItem();
        bids.close();
        if (res != null) {
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
