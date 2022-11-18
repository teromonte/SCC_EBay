package main.java.business.session;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Cookie;
import main.java.DAL.cache.CachePlus;
import main.java.models.entities.Session;

public class CheckCookieUseCase {
    /**
     * Throws exception if not appropriate user for operation on Auction
     */
    public static Session checkCookieUser(Cookie session, String id) throws NotAuthorizedException {
        if (session == null || session.getValue() == null) throw new NotAuthorizedException("No session initialized");
        Session s;
        try {
            s = CachePlus.getSession(session.getValue());
        } catch (Exception e) {
            throw new NotAuthorizedException("No valid session initialized");
        }
        if (s == null || s.getUser() == null || s.getUser().length() == 0)
            throw new NotAuthorizedException("No valid session initialized");
        if (!s.getUser().equals(id) && !s.getUser().equals("admin"))
            throw new NotAuthorizedException("Invalid user : " + s.getUser());
        return s;
    }

}
