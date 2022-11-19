package main.java.business.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.core.Response;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.DAO.QuestionDAO;
import main.java.models.entities.Question;

public class AddQuestionUseCase {

    public static Response addQuestion(Question question, String auctionID) throws JsonProcessingException {
        IQuestionGateway questionGateway = new QuestionRepository();
        IAuctionGateway auctionGateway = new AuctionRepository();
        QuestionDAO questionDAO = new QuestionDAO(question);

        try {
            AuctionDAO a = auctionGateway.getAuctionById(auctionID);

            QuestionDAO q = questionGateway.addQuestion(questionDAO, a.getId()).getItem();

            return Response.ok(q).build();
        } catch (Exception e) {
            throw e;
        }

    }
}
