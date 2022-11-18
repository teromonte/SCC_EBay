package main.java.DAL.cache;

import main.java.DAL.RedisCache;
import main.java.models.entities.Session;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CachePlus {
    public static List<String> cacheGet(String type, String pid) { //pid -> type is "part of" object with id <pid>
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            switch (type) {
                case "auctionL":
                    return jedis.lrange(type, 0, -1);
                case "bidL":
                    return jedis.lrange(type + ":" + pid, 0, -1);
                case "questionL":
                    return jedis.lrange(type + ":" + pid, 0, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String cacheGet(String id) {
        try (Jedis jedis = RedisCache.getCachePool().getResource()) {
            return jedis.get("user:" + id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
            return new Session(value, user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

