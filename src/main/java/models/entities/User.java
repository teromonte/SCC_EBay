package main.java.models.entities;

/**
 * Represents a User, as returned to the clients
 */
public class User {
    private String id; // code
    private String name;
    private String pwd;
    private String photoId;

    public User() {
        super();
    }

    public User(String id, String name, String pwd, String photoId) {
        super();
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", pwd='" + pwd + '\'' + ", photoId='" + photoId + '\'' + '}';
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

}
