package main.java.business.auction;

import java.util.List;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.cache.CachePlus;
import main.java.models.DAO.AuctionDAO;

public class ListAuctionsFromUserUseCase {

    public static CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID) {
		IAuctionGateway auctionGateway = new AuctionRepository();
        return auctionGateway.listAuctionsFromUser(userID);
    }
	
	public static List<String> cacheListAuctionsFromUser(String userID) throws Exception {
		List<String> res = CachePlus.cacheGet(CachePlus.USER_AUCTIONS, userID);
		if(res==null)
			throw new Exception("Not in cache");
		return res;
    }
}