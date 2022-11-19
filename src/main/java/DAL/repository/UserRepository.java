package main.java.DAL.repository;

import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IUserGateway;
import main.java.models.DAO.UserDAO;
import redis.clients.jedis.Jedis;

public class UserRepository implements IUserGateway {
    private CosmosDBLayer users;


    public UserRepository() {
        this.users = getDBLayer();
    }

    private CosmosDBLayer getDBLayer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.USER_CONTAINER);
        return db;
    }

    @Override
    public CosmosItemResponse<Object> delUserById(String id) {
        PartitionKey key = new PartitionKey(id);
        CosmosItemResponse<Object> res = users.getContainer().deleteItem(id, key, new CosmosItemRequestOptions());
        users.close();
        if (res.getStatusCode() < 300) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                jedis.del("user:" + id);
                jedis.del(CachePlus.AUCTION_LIST);
				jedis.rpush(CachePlus.TRASH, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public CosmosItemResponse<UserDAO> putUser(UserDAO user) {
        CosmosItemResponse<UserDAO> res;
        try {
            var u = getUserById(user.getId());
            PartitionKey key = new PartitionKey(user.getId());
            res = users.getContainer().replaceItem(user, user.getId(), key, new CosmosItemRequestOptions());

        } catch (Exception e) {
            res = users.getContainer().createItem(user);
        }
        users.close();
        if (res.getStatusCode() < 300) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                ObjectMapper mapper = new ObjectMapper();
                jedis.set("user:" + user.getId(), mapper.writeValueAsString(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    @Override
    public CosmosPagedIterable<UserDAO> getUserById(String id) {
        var res = users.getContainer().queryItems("SELECT * FROM users WHERE users.id=\"" + id + "\"", new CosmosQueryRequestOptions(), UserDAO.class);
        users.close();

        return res;
    }

    @Override
    public CosmosPagedIterable<UserDAO> getUsers() {
        var res = users.getContainer().queryItems("SELECT * FROM users ", new CosmosQueryRequestOptions(), UserDAO.class);
        users.close();

        return res;
    }
}
