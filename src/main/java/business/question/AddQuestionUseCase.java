package main.java.business.question;

import com.azure.cosmos.models.CosmosItemResponse;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.DAO.QuestionDAO;
import main.java.models.entities.Question;

public class AddQuestionUseCase {

	public static CosmosItemResponse<QuestionDAO> addQuestion(Question question, String auctionID) {
		IQuestionGateway questionGateway = new QuestionRepository();
        IAuctionGateway auctionGateway = new AuctionRepository();
        QuestionDAO questionDAO = new QuestionDAO(question);

        if (question.getReply().isEmpty()) {
            return questionGateway.addQuestion(questionDAO, auctionID);
        } else {
            AuctionDAO auction = auctionGateway.getAuctionById(auctionID).stream().findFirst().get();
            if (auction.getOwner().equals(question.getUser())) {
                return questionGateway.addQuestion(questionDAO, auctionID);
            }
        }

        return null;
    }
}
