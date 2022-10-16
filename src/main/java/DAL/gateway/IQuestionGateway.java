package main.java.DAL.gateway;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.models.DAO.QuestionDAO;

public interface IQuestionGateway {
    CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID);

    CosmosItemResponse<QuestionDAO> addQuestion(QuestionDAO questionDAO, String auctionID);
}
