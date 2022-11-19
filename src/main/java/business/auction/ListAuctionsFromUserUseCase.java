package main.java.business.auction;

import com.azure.cosmos.util.CosmosPagedIterable;
import jakarta.ws.rs.NotFoundException;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

import java.util.List;

import static main.java.srv.MainApplication.CACHE_FLAG;

public class ListAuctionsFromUserUseCase {

    public static CosmosPagedIterable<AuctionDAO> listAuctionsFromUser(String userID) {
        IAuctionGateway auctionGateway = new AuctionRepository();
        return auctionGateway.listAuctionsFromUser(userID);
    }

    public static List<String> cacheListAuctionsFromUser(String userID) throws NotFoundException {
        if (!CACHE_FLAG) throw new NotFoundException("Cache off");
        List<String> res = CachePlus.cacheGet(CachePlus.USER_AUCTIONS, userID);
        if (res == null) throw new NotFoundException("Not in cache");
        else if (res.isEmpty()) throw new NotFoundException("Not in cache");
        else return res;
    }
}