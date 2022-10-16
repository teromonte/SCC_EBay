package main.java.business.question;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.BidDAO;
import main.java.models.DAO.QuestionDAO;
import main.java.models.entities.Bid;
import main.java.models.entities.Question;

public class AddQuestionUseCase {
    IQuestionGateway questionGateway;

    public AddQuestionUseCase() {
        questionGateway = new QuestionRepository();
    }
    public CosmosItemResponse<QuestionDAO> addQuestion(Question question, String auctionID) {
        QuestionDAO questionDAO = new QuestionDAO(question);
        return questionGateway.addQuestion(questionDAO, auctionID);
    }
}
