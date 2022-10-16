package main.java.business.user;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.User;

public class AddUserUseCase {
    IUserGateway userGateway;
    public AddUserUseCase() {
        userGateway = new UserRepository();
    }

    public CosmosItemResponse<UserDAO> addUser(User user) {
        UserDAO userDAO = new UserDAO(user);
        return userGateway.putUser(userDAO);
    }
}
