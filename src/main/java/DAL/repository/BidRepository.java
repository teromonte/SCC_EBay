package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import redis.clients.jedis.Jedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IBidGateway;
import main.java.models.DAO.BidDAO;

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
    public CosmosItemResponse<BidDAO> addBid(BidDAO bidDAO, String auctionID) {
        String id = "0:" + System.currentTimeMillis();
        bidDAO.setId(id);
        CosmosItemResponse<BidDAO> res = bids.getContainer().createItem(bidDAO);
        bids.close();
		if(res.getStatusCode() < 300) {
			try (Jedis jedis = RedisCache.getCachePool().getResource()) {
				ObjectMapper mapper = new ObjectMapper();
				jedis.set("bid:"+bidDAO.getId(), mapper.writeValueAsString(bidDAO));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
    }
}
