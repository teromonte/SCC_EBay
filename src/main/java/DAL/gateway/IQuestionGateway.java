package main.java.DAL.gateway;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.core.JsonProcessingException;
import main.java.models.DAO.QuestionDAO;

public interface IQuestionGateway {
    CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID);

    CosmosItemResponse<QuestionDAO> addQuestion(QuestionDAO questionDAO, String auctionID) throws JsonProcessingException;

    QuestionDAO getQuestionById(String id);

    CosmosItemResponse<QuestionDAO> addReply(String reply, QuestionDAO questionDAO) throws JsonProcessingException;
}
