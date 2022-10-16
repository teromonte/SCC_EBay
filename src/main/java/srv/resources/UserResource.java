package main.java.srv.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.business.user.AddUserUseCase;
import main.java.business.user.DeleteUserByIDUseCase;
import main.java.business.user.GetUserByIDUseCase;
import main.java.models.entities.User;

/**
 * Class with control endpoints.
 */
@Path("/user")
public class UserResource {
    /**
     * if user already exists, we replace the old user by the new one
     *
     * @param user
     * @return
     */
    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addUser(User user) {
        AddUserUseCase userUseCase = new AddUserUseCase();
        return userUseCase.addUser(user).getItem().toString();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") String id) {
        GetUserByIDUseCase userUseCase = new GetUserByIDUseCase();
        return userUseCase.getUserByID(id).stream().findFirst().get().toString();
    }

    @Path("/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") String id) throws JsonProcessingException {
        DeleteUserByIDUseCase userUseCase = new DeleteUserByIDUseCase();
        return userUseCase.DeleteUserByID(id).getResponseHeaders().toString();
    }
}
