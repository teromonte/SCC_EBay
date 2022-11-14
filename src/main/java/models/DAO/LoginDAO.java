package main.java.models.DAO;

public class LoginDAO {
    private String _rid;
    private String _ts;
    private String user;
    private String pwd;

    public LoginDAO(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
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
