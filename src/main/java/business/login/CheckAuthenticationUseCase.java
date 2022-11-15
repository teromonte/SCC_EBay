package main.java.business.login;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import main.java.models.entities.Login;

import java.util.UUID;

public class CheckAuthenticationUseCase {
    public static Response check(Login user) {
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
