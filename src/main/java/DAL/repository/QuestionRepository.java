package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import redis.clients.jedis.Jedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisLayer;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.models.DAO.QuestionDAO;

public class QuestionRepository implements IQuestionGateway {
    public QuestionRepository() {
    }

    private CosmosContainer getContainer() {
        CosmosDBLayer db = CosmosDBLayer.getInstance();
        db.init(CosmosDBLayer.QUESTION_CONTAINER);
        return db.getContainer();
    }

    @Override
    public CosmosPagedIterable<QuestionDAO> listQuestions(String auctionID) {
        CosmosContainer questions = getContainer();
		CosmosPagedIterable<QuestionDAO> pi = questions.queryItems("SELECT * FROM questions WHERE questions.auction=\"" + auctionID + "\"", new CosmosQueryRequestOptions(), QuestionDAO.class);
		try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
			ObjectMapper mapper = new ObjectMapper();
			for(QuestionDAO item : pi)
			    jedis.rpush("questionL:" + auctionID, mapper.writeValueAsString(item));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return pi;
    }

    @Override
    public CosmosItemResponse<QuestionDAO> addQuestion(QuestionDAO questionDAO, String auctionID) {
        CosmosContainer questions = getContainer();
        String id = "0:" + System.currentTimeMillis();
        questionDAO.setId(id);
        CosmosItemResponse<QuestionDAO> res = questions.createItem(questionDAO);
		if(res.getStatusCode() < 300) {
			try (Jedis jedis = RedisLayer.getCachePool().getResource()) {
				ObjectMapper mapper = new ObjectMapper();
				jedis.set("question:"+questionDAO.getId(), mapper.writeValueAsString(questionDAO));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
    }
}
