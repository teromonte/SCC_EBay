package main.java.models.DAO;

import main.java.models.entities.Login;

public class LoginDAO {
    private String _rid;
    private String _ts;
    private String user;
    private String pwd;

    public LoginDAO(String user, String pwd) {
        super();
        this.user = user;
        this.pwd = pwd;
    }

    public LoginDAO(Login login) {
        this(login.getUser(), login.getPwd());
    }

    public LoginDAO() {

    }

    @Override
    public String toString() {
        return "LoginDAO{" + "user='" + user + '\'' + ", pwd='" + pwd + '\'' + '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
