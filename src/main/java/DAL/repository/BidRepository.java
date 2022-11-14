package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import redis.clients.jedis.Jedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisLayer;
import main.java.DAL.gateway.IBidGateway;
import main.java.models.DAO.BidDAO;

public class BidRepository implements IBidGateway {
    public BidRepository() {
    }

    private CosmosContainer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.BID_CONTAINER);
        return db.getContainer();
    }


    @Override
    public CosmosPagedIterable<BidDAO> listBids(String auctionID) {
        CosmosContainer bids = getContainer();
        return bids.queryItems("SELECT * FROM bids WHERE bids.auction=\"" + auctionID + "\"", new CosmosQueryRequestOptions(), BidDAO.class);
    }

    @Override
    public CosmosItemResponse<BidDAO> addBid(BidDAO bidDAO, String auctionID) {
        CosmosContainer bids = getContainer();
        String id = "0:" + System.currentTimeMillis();
        bidDAO.setId(id);
        CosmosItemResponse<BidDAO> res = bids.createItem(bidDAO);
		if(res.getStatusCode() < 300) {
			try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
				ObjectMapper mapper = new ObjectMapper();
				jedis.set("bid:"+bidDAO.getId(), mapper.writeValueAsString(bidDAO));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
    }
}
