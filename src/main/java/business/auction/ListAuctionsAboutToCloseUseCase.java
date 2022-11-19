package main.java.business.auction;

import jakarta.ws.rs.NotFoundException;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

import java.util.List;

import static main.java.srv.MainApplication.CACHE_FLAG;

public class ListAuctionsAboutToCloseUseCase {

    public static List<AuctionDAO> listAuctionsAboutToClose() {
        IAuctionGateway auctionGateway = new AuctionRepository();
        var allAcutions = auctionGateway.listAllAuctionsAboutToClose().stream().toList();
        return allAcutions;
    }

    public static List<String> cacheListAuctionsAboutToClose() throws NotFoundException {
        if (CACHE_FLAG) throw new NotFoundException();
        List<String> res = CachePlus.cacheGet(CachePlus.AUCTION_LIST, null);
        if (res == null) throw new NotFoundException("Not in cache");
        else if (res.isEmpty()) throw new NotFoundException("Not in cache");
        else return res;
    }
}
