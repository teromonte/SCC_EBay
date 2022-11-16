package main.java.models.entities;

public class Session {
    private String user;
    private String uid;

    public Session() {
        super();
    }

    public Session(String uid, String user) {
        super();
        this.uid = uid;
        this.user = user;
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
