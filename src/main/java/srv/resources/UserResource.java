package main.java.srv.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import main.java.business.auction.ListAuctionsFromUserUseCase;
import main.java.business.user.AddUserUseCase;
import main.java.business.user.CheckAuthenticationUseCase;
import main.java.business.user.DeleteUserByIDUseCase;
import main.java.business.user.GetUserByIDUseCase;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.Login;
import main.java.models.entities.User;
import main.java.utils.GenericExceptionMapper;

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
    public Response addUser(User user) {
        UserDAO u;
        try {
            u = AddUserUseCase.addUser(user).getItem();
            return Response.ok(u).build();
        } catch (Exception e) {
            GenericExceptionMapper g = new GenericExceptionMapper();
            return g.toResponse(e);

        }
    }

    @Path("/{userID}/auctions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAuctionsFromUser(@PathParam("userID") String userID) {
        try {
            var res = ListAuctionsFromUserUseCase.cacheListAuctionsFromUser(userID);
            return Response.ok(res).build();
        } catch (NotFoundException e) {
            var res = ListAuctionsFromUserUseCase.listAuctionsFromUser(userID).stream().toList();
            return Response.ok(res).build();
        } catch (Exception ee) {
            GenericExceptionMapper g = new GenericExceptionMapper();
            return g.toResponse(ee);
        }
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        try {


            var r = GetUserByIDUseCase.cacheGetUserByID(id);
            if (r != null) {
                return Response.ok(r).build();
            } else {
                var res = GetUserByIDUseCase.getUserByID(id).stream().findFirst().get();
                return Response.ok(res).build();
            }

        } catch (Exception ee) {
            GenericExceptionMapper g = new GenericExceptionMapper();
            return g.toResponse(ee);
        }

    }

    @Path("/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") String id) {
        return DeleteUserByIDUseCase.DeleteUserByID(id).getResponseHeaders().toString();
    }

    @POST
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(Login user) {
        return CheckAuthenticationUseCase.check(user);

    }

}
