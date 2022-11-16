package main.java.business.user;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import main.java.DAL.gateway.IUserGateway;
import main.java.DAL.repository.UserRepository;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.Login;
import main.java.models.entities.Session;

import java.util.UUID;

public class CheckAuthenticationUseCase {
    public static Response check(Login user) {
        IUserGateway userGateway = new UserRepository();
        UserDAO u = userGateway.getUserById(user.getId()).stream().findFirst().get();
        // Check pwd
        if (u.getPwd().equals(user.getPwd())) {
            String uid = UUID.randomUUID().toString();
            NewCookie cookie = new NewCookie.Builder("scc:session").value(uid).path("/").comment("sessionid").maxAge(3600).secure(false).httpOnly(true).build();
            //RedisLayer.getInstance().putSession(new Session(uid, user.getUser()));
            return Response.ok().cookie(cookie).build();
        } else throw new NotAuthorizedException("Incorrect login");
    }

}
