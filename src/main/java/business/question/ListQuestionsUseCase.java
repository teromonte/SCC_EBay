package main.java.business.question;

import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.BidDAO;
import main.java.models.DAO.QuestionDAO;

public class ListQuestionsUseCase {
    IQuestionGateway questionGateway;

    public ListQuestionsUseCase() {
        questionGateway = new QuestionRepository();
    }

    public CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID) {
        return questionGateway.listQuestions(auctionID);
    }
}
