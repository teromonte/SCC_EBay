package main.java.business.user;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;
import main.java.models.DAO.UserDAO;

public class GetUserByIDUseCase {

	public static CosmosPagedIterable<UserDAO> getUserByID(String id) {
		IUserGateway userGateway = new UserRepository();
        return userGateway.getUserById(id);
    }
}
