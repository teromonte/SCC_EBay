package main.java.DAL.repository;

import redis.clients.jedis.Jedis;
import java.util.List;
import main.java.DAL.RedisLayer;

public class CachePlus {
	public static List<String> cacheGet(String type, String pid) { //pid -> type is "part of" object with id <pid>
		try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
			switch(type) {
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
		try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
			return jedis.get("user:"+id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
