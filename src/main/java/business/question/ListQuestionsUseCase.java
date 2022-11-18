package main.java.business.question;

import java.util.List;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.DAL.repository.QuestionRepository;
import main.java.DAL.cache.CachePlus;
import main.java.models.DAO.BidDAO;
import main.java.models.DAO.QuestionDAO;

public class ListQuestionsUseCase {

	public static CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID) {
		IQuestionGateway questionGateway = new QuestionRepository();
        return questionGateway.listQuestions(auctionID);
    }
	
	public static List<String> cacheListQuestions(String auctionID) throws Exception {
		List<String> res = CachePlus.cacheGet(CachePlus.QUESTION_LIST, auctionID);
		if(res==null)
			throw new Exception("Not in cache");
		return res;
    }
}
