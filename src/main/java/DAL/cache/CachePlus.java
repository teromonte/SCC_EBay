package main.java.DAL.cache;

import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.RedisCache;
import main.java.models.DAO.AuctionDAO;
import main.java.models.DAO.QuestionDAO;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.Session;
import redis.clients.jedis.Jedis;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static main.java.srv.MainApplication.CACHE_FLAG;

public class CachePlus {
    public static String AUCTION_LIST = "auctionL";
    public static String BID_LIST = "bidL";
    public static String QUESTION_LIST = "questionL";
    public static String USER_AUCTIONS = "userAuc";
    public static String TRASH = "deleted";

    public static List<String> cacheGet(String type, String pid) { //pid -> type is "part of" object with id <pid>
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            if (type.equals(AUCTION_LIST)) return jedis.lrange(type, 0, -1);
            else if (type.equals(BID_LIST)) return jedis.lrange(type + ":" + pid, 0, -1);
            else if (type.equals(QUESTION_LIST)) return jedis.lrange(type + ":" + pid, 0, -1);
            else if (type.equals(USER_AUCTIONS)) return jedis.lrange(type + ":" + pid, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String cacheGet(String id)  {
        if (!CACHE_FLAG) return null;
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            var user = jedis.get("user:" + id);
            return user;
        }
    }

    public static <T> CosmosPagedIterable<T> cacheThenCPI(CosmosPagedIterable<T> pi, String auctionID, String type) {
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            ObjectMapper mapper = new ObjectMapper();
            if (type.equals(AUCTION_LIST)) {
                jedis.del(type);
                for (T item : pi)
                    jedis.rpush(type, mapper.writeValueAsString(item));
            } else if (type.equals(BID_LIST)) {
                jedis.del(type + ":" + auctionID);
                for (T item : pi)
                    jedis.rpush(type + ":" + auctionID, mapper.writeValueAsString(item));
            } else if (type.equals(QUESTION_LIST)) {
                jedis.del(type + ":" + auctionID);
                for (T item : pi)
                    jedis.rpush(type + ":" + auctionID, mapper.writeValueAsString(item));
            } else if (type.equals(USER_AUCTIONS)) { //auctionID is actually user ID in this case
                jedis.del(type + ":" + auctionID);
                for (T item : pi)
                    jedis.rpush(type + ":" + auctionID, mapper.writeValueAsString(item));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static String putSession(Session s) {
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            return jedis.set("session:" + s.getUid(), s.getUser());
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static Session getSession(String value) {
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            var user = jedis.get("session:" + value);
            if (user.isEmpty()) throw new NotFoundException();
            return new Session(value, user);
        }
    }

    public static AuctionDAO getAuction(String id) throws JsonProcessingException {
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            var user = jedis.get("auction:" + id);
            if (user.isEmpty()) throw new NotFoundException();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(user, AuctionDAO.class);
        }
    }

    public static QuestionDAO getQuestion(String id) throws JsonProcessingException {
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            var user = jedis.get("question:" + id);
            if (user.isEmpty()) throw new NotFoundException();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(user, QuestionDAO.class);
        }
    }

}

