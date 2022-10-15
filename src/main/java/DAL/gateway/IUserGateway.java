package main.java.DAL.gateway;

import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.User;

import java.util.List;

public interface IUserGateway {

    public CosmosItemResponse<Object> delUserById(String id);

    public CosmosItemResponse<UserDAO> putUser(UserDAO user);

    public CosmosPagedIterable<UserDAO> getUserById(String id);

    public CosmosPagedIterable<UserDAO> getUsers();
}
