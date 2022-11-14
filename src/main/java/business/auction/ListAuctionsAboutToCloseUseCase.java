package main.java.business.auction;

import java.util.List;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.repository.CachePlus;
import main.java.models.DAO.AuctionDAO;

public class ListAuctionsAboutToCloseUseCase {

    public static CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose() {
		IAuctionGateway auctionGateway = new AuctionRepository();
        return auctionGateway.listAuctionsAboutToClose();
    }
	
	public static List<String> cacheListAuctionsAboutToClose() {
		return CachePlus.cacheGet("auctionL", null);
    }
}
