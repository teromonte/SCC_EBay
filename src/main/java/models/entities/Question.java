package main.java.models.entities;

public class Question {
    private String id;
    private String auction;
    private String user;
    private String text;

    public Question(String id, String auction, String user, String text) {
        this.id = id;
        this.auction = auction;
        this.user = user;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question{" + "id='" + id + '\'' + ", auction='" + auction + '\'' + ", user='" + user + '\'' + ", text='" + text + '\'' + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuction() {
        return auction;
    }

    public void setAuction(String auction) {
        this.auction = auction;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
