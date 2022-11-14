package main.java.business.question;

import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.BidDAO;
import main.java.models.DAO.QuestionDAO;

public class ListQuestionsUseCase {

	public static CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID) {
		IQuestionGateway questionGateway = new QuestionRepository();
        return questionGateway.listQuestions(auctionID);
    }
}
