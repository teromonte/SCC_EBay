package main.java.srv.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import main.java.business.user.AddUserUseCase;
import main.java.business.user.DeleteUserByIDUseCase;
import main.java.business.user.GetUserByIDUseCase;
import main.java.models.entities.Login;
import main.java.models.entities.User;

import java.util.UUID;

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

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(Login user) {
        boolean pwdOk = false;
        // Check pwd
        if( pwdOk) {
            String uid = UUID.randomUUID().toString();
            NewCookie cookie = new NewCookie.Builder("scc:session")
                    .value(uid)
                    .path("/")
                    .comment("sessionid")
                    .maxAge(3600)
                    .secure(false)
                    .httpOnly(true)
                    .build();
            //RedisLayer.getInstance().putSession( new Session( uid, user.getUser()));
            return Response.ok().cookie(cookie).build();
        } else
            throw new NotAuthorizedException("Incorrect login");
    }

}
