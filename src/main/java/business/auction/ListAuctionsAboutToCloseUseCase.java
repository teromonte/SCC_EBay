package main.java.business.auction;

import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

import java.util.List;

public class ListAuctionsAboutToCloseUseCase {

    public static CosmosPagedIterable<AuctionDAO> listAuctionsAboutToClose() {
        IAuctionGateway auctionGateway = new AuctionRepository();
        return auctionGateway.listAuctionsAboutToClose();
    }

    public static List<String> cacheListAuctionsAboutToClose() throws Exception {
        List<String> res = CachePlus.cacheGet(CachePlus.AUCTION_LIST, null);
        if (res == null) throw new Exception("Not in cache");
        else if (res.isEmpty()) throw new Exception("Not in cache");
        else return res;
    }
}
