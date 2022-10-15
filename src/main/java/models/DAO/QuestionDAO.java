package main.java.models.DAO;

import main.java.models.entities.Question;

public class QuestionDAO {
    private String _rid;
    private String _ts;
    private String id;
    private String auction;
    private String user;
    private String text;

    public QuestionDAO(String id, String auction, String user, String text) {
        super();
        this.id = id;
        this.auction = auction;
        this.user = user;
        this.text = text;
    }

    public QuestionDAO() {

    }

    public QuestionDAO(Question question) {
        this(question.getId(), question.getAuction(), question.getText(), question.getUser());
    }

    public Question toQuestion() {
        return new Question(id, auction, user, text);
    }

    public String get_rid() {
        return _rid;
    }

    public void set_rid(String _rid) {
        this._rid = _rid;
    }

    @Override
    public String toString() {
        return "QuestionDAO{" + "_rid='" + _rid + '\'' + ", _ts='" + _ts + '\'' + ", id='" + id + '\'' + ", auction='" + auction + '\'' + ", user='" + user + '\'' + ", text='" + text + '\'' + '}';
    }

    public String get_ts() {
        return _ts;
    }

    public void set_ts(String _ts) {
        this._ts = _ts;
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
