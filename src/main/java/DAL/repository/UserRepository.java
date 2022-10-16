package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.gateway.IUserGateway;
import main.java.models.DAO.UserDAO;

public class UserRepository implements IUserGateway {

    public UserRepository() {
    }

    private CosmosContainer getContainer()
    {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.USER_CONTAINER);
        return db.getContainer();
    }

    @Override
    public CosmosItemResponse<Object> delUserById(String id) {

        CosmosContainer users = getContainer();

        PartitionKey key = new PartitionKey(id);
        return users.deleteItem(id, key, new CosmosItemRequestOptions());
    }

    @Override
    public CosmosItemResponse<UserDAO> putUser(UserDAO user) {
        CosmosContainer users = getContainer();

        String id = "0:" + System.currentTimeMillis();
        user.setId(id);

        return users.createItem(user);
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
