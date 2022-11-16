package main.java.models.DAO;

import main.java.models.entities.Session;

public class SessionDAO {
    private String _rid;
    private String _ts;
    private String uid;
    private String user;

    public SessionDAO(String uid, String user) {
        super();
        this.user = user;
        this.uid = uid;
    }

    public SessionDAO() {

    }

    public SessionDAO(Session Session) {
        this(Session.getUid(), Session.getUser());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
