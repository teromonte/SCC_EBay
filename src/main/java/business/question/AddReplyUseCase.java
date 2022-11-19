package main.java.business.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.core.Response;
import main.java.DAL.gateway.IAuctionGateway;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.AuctionRepository;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.AuctionDAO;
import main.java.models.DAO.QuestionDAO;
import main.java.models.entities.Reply;

public class AddReplyUseCase {
    public static Response addReply(Reply reply, String auctionID, String questionID, String user) throws JsonProcessingException {
        IQuestionGateway questionGateway = new QuestionRepository();
        IAuctionGateway auctionGateway = new AuctionRepository();

        try {
            AuctionDAO a = auctionGateway.getAuctionById(auctionID);
            QuestionDAO q = questionGateway.getQuestionById(questionID);

            if (a.getOwner().equals(user)) {
                questionGateway.addReply(reply.getReply(), q);
            }
            return Response.ok(q).build();
        } catch (Exception e) {
            throw e;
        }

    }
}

