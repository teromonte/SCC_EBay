package main.java.business.question;

import com.azure.cosmos.util.CosmosPagedIterable;
import jakarta.ws.rs.NotFoundException;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.QuestionRepository;
import main.java.models.DAO.QuestionDAO;

import java.util.List;

public class ListQuestionsUseCase {

    public static CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID) {
        IQuestionGateway questionGateway = new QuestionRepository();
        return questionGateway.listQuestions(auctionID);
    }

    public static List<String> cacheListQuestions(String auctionID) throws NotFoundException {
        List<String> res = CachePlus.cacheGet(CachePlus.QUESTION_LIST, auctionID);
        if (res == null) throw new NotFoundException("Not in cache");
        else if (res.isEmpty()) throw new NotFoundException("Not in cache");
        else return res;
    }
}
