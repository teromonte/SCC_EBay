package main.java.business.user;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;

public class DeleteUserByIDUseCase {
    IUserGateway userGateway;

    public DeleteUserByIDUseCase() {
        userGateway = new UserRepository();
    }

    public CosmosItemResponse<Object> DeleteUserByID(String id) {
        return userGateway.delUserById(id);
    }
}
