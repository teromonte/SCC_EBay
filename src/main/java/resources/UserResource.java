package main.java.resources;

import com.azure.cosmos.models.CosmosItemResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import main.java.business.AddUserUseCase;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.User;

/**
 * Class with control endpoints.
 */
@Path("/user")
public class UserResource {

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addUser(User user) {
        AddUserUseCase userUseCase = new AddUserUseCase();
        return userUseCase.addUser(user).toString();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        //return mapper.writeValueAsString(user);
        return null;
    }

    @Path("/{id}/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //return mapper.writeValueAsString(user);
        return null;
    }
}
