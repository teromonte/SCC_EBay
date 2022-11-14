package main.java.business.user;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.User;

public class AddUserUseCase {

	public static CosmosItemResponse<UserDAO> addUser(User user) {
		IUserGateway userGateway = new UserRepository();
        UserDAO userDAO = new UserDAO(user);
        return userGateway.putUser(userDAO);
    }
}
