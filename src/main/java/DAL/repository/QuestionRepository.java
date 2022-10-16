package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.models.DAO.QuestionDAO;

public class QuestionRepository implements IQuestionGateway {
    public QuestionRepository() {
    }

    private CosmosContainer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.QUESTION_CONTAINER);
        return db.getContainer();
    }

    @Override
    public CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID) {
        CosmosContainer questions = getContainer();
        return questions.queryItems("SELECT * FROM questions WHERE questions.auction=\"" + auctionID + "\"", new CosmosQueryRequestOptions(), QuestionDAO.class);
    }

    @Override
    public CosmosItemResponse<QuestionDAO> addQuestion(QuestionDAO questionDAO, String auctionID) {
        CosmosContainer questions = getContainer();
        String id = "0:" + System.currentTimeMillis();
        questionDAO.setId(id);
        return questions.createItem(questionDAO);
    }
}
