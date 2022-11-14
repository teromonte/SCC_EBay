package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import redis.clients.jedis.Jedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisLayer;
import main.java.DAL.gateway.IUserGateway;
import main.java.models.DAO.UserDAO;

public class UserRepository implements IUserGateway {

    public UserRepository() {
    }

    private CosmosContainer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.USER_CONTAINER);
        return db.getContainer();
    }

    @Override
    public CosmosItemResponse<Object> delUserById(String id) {
        CosmosContainer users = getContainer();
        PartitionKey key = new PartitionKey(id);
        CosmosItemResponse<Object> res = users.deleteItem(id, key, new CosmosItemRequestOptions());
		if(res.getStatusCode() < 300) {
			try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
				jedis.del("user:"+id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
    }

    @Override
    public CosmosItemResponse<UserDAO> putUser(UserDAO user) {
        CosmosContainer users = getContainer();
		CosmosItemResponse<UserDAO> res;
        var u = getUserById(user.getId());
        if (u == null) {
            String id = "0:" + System.currentTimeMillis();
            user.setId(id);
            res = users.createItem(user);
        } else {
            PartitionKey key = new PartitionKey(user.getId());
            res = users.replaceItem(user, user.getId(), key, new CosmosItemRequestOptions());
        }
		if(res.getStatusCode() < 300) {
			try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
				ObjectMapper mapper = new ObjectMapper();
				jedis.set("user:"+user.getId(), mapper.writeValueAsString(user));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
    }

    @Override
    public CosmosPagedIterable<UserDAO> getUserById(String id) {
        CosmosContainer users = getContainer();
        return users.queryItems("SELECT * FROM users WHERE users.id=\"" + id + "\"", new CosmosQueryRequestOptions(), UserDAO.class);
    }

    @Override
    public CosmosPagedIterable<UserDAO> getUsers() {
        CosmosContainer users = getContainer();
        return users.queryItems("SELECT * FROM users ", new CosmosQueryRequestOptions(), UserDAO.class);
    }
}
