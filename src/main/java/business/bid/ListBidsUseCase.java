package main.java.business.bid;

import java.util.List;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IBidGateway;
import main.java.DAL.repository.BidRepository;
import main.java.DAL.cache.CachePlus;
import main.java.models.DAO.BidDAO;
import main.java.models.entities.Bid;

public class ListBidsUseCase {

    public static CosmosPagedIterable<BidDAO> listBids(String auctionID) {
		IBidGateway bidGateway = new BidRepository();
        return bidGateway.listBids(auctionID);
    }
	
	public static List<String> cacheListBids(String auctionID) throws Exception {
		List<String> res = CachePlus.cacheGet(CachePlus.BID_LIST, auctionID);
		if(res==null)
			throw new Exception("Not in cache");
		return res;
    }
}
