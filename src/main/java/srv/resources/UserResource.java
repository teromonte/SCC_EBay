package main.java.srv.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.business.user.AddUserUseCase;
import main.java.business.login.CheckAuthenticationUseCase;
import main.java.business.user.DeleteUserByIDUseCase;
import main.java.business.user.GetUserByIDUseCase;
import main.java.models.entities.Login;
import main.java.models.entities.User;

/**
 * Class with user endpoints.
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
        return AddUserUseCase
		.addUser(user)
		.getItem().toString();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") String id) {
        return GetUserByIDUseCase
		.getUserByID(id)
		.stream().findFirst().get().toString();
    }

    @Path("/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") String id) throws JsonProcessingException {
        return DeleteUserByIDUseCase
		.DeleteUserByID(id)
		.getResponseHeaders().toString();
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(Login user) {
        return CheckAuthenticationUseCase.check(user);
    }

}
