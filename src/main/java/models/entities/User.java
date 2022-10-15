package main.java.models.entities;

/**
 * Represents a User, as returned to the clients
 */
public class User {
    private String id;
    private String name;
    private String nickname;
    private String pwd;
    private String photoId;

    public User(String id, String name, String nickname, String pwd, String photoId) {
        super();
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.pwd = pwd;
        this.photoId = photoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String name) {
        this.nickname = nickname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", nickname='" + nickname + '\'' + ", pwd='" + pwd + '\'' + ", photoId='" + photoId + '\'' + '}';
    }
}
