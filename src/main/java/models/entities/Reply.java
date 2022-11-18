package main.java.models.entities;

public class Reply {
    private String reply;

    public Reply(String reply) {
        this.reply = reply;
    }

    public Reply() {
        super();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
