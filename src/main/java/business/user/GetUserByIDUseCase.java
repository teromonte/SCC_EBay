package main.java.business.user;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;
import main.java.models.DAO.UserDAO;

public class GetUserByIDUseCase {
    IUserGateway userGateway;

    public GetUserByIDUseCase() {
        userGateway = new UserRepository();
    }

    public CosmosPagedIterable<UserDAO> getUserByID(String id) {
        return userGateway.getUserById(id);
    }
}
