package main.java.business.auction;

import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.models.DAO.AuctionDAO;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListAuctionsAboutToCloseUseCase {

    public static List<AuctionDAO> listAuctionsAboutToClose() {
        List<AuctionDAO> res = null;
        IAuctionGateway auctionGateway = new AuctionRepository();
        var allAcutions = auctionGateway.listAllAuctionsOpen().stream().toList();
        for (AuctionDAO a : allAcutions) {
            Date d = a.getEndTime();
            Date now = Date.from(Instant.now());
            long diffInMillies = Math.abs(now.getTime() - d.getTime());
            long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff < 60 * 5) {
                res.add(a);
            }
        }
        return res;
    }

    public static List<String> cacheListAuctionsAboutToClose() throws Exception {
        List<String> res = CachePlus.cacheGet(CachePlus.AUCTION_LIST, null);
        if (res == null) throw new Exception("Not in cache");
        else if (res.isEmpty()) throw new Exception("Not in cache");
        else return res;
    }
}
