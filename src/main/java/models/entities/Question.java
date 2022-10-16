package main.java.models.entities;

public class Question {
    private String id;
    private String auction;
    private String user;
    private String text;
    private String reply;

    public Question() {
        super();
    }

    public Question(String id, String auction, String user, String text, String reply) {
        this.id = id;
        this.auction = auction;
        this.user = user;
        this.text = text;
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Question{" + "id='" + id + '\'' + ", auction='" + auction + '\'' + ", user='" + user + '\'' + ", text='" + text + '\'' + ", reply='" + reply + '\'' + '}';
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
