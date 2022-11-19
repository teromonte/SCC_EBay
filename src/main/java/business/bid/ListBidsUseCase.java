package main.java.business.bid;

import com.azure.cosmos.util.CosmosPagedIterable;
import jakarta.ws.rs.NotFoundException;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IBidGateway;
import main.java.DAL.repository.BidRepository;
import main.java.models.DAO.BidDAO;

import java.util.List;

public class ListBidsUseCase {

    public static CosmosPagedIterable<BidDAO> listBids(String auctionID) {
        IBidGateway bidGateway = new BidRepository();
        return bidGateway.listBids(auctionID);
    }

    public static List<String> cacheListBids(String auctionID) throws NotFoundException{
        List<String> res = CachePlus.cacheGet(CachePlus.BID_LIST, auctionID);
        if (res == null) throw new NotFoundException();
        else if (res.isEmpty()) throw new NotFoundException();
        else return res;
    }
}
