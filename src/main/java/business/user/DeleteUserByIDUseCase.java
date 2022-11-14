package main.java.business.user;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;

public class DeleteUserByIDUseCase {

	public static CosmosItemResponse<Object> DeleteUserByID(String id) {
		IUserGateway userGateway = new UserRepository();
        return userGateway.delUserById(id);
    }
}
