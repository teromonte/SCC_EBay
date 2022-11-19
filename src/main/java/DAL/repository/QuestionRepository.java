package main.java.DAL.repository;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.NotFoundException;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.RedisCache;
import main.java.DAL.cache.CachePlus;
import main.java.DAL.gateway.IQuestionGateway;
import main.java.models.DAO.QuestionDAO;
import redis.clients.jedis.Jedis;

import static main.java.srv.MainApplication.CACHE_FLAG;

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
        if (CACHE_FLAG) CachePlus.cacheThenCPI(pi, auctionID, CachePlus.QUESTION_LIST);
        return pi;

    }

    @Override
    public CosmosItemResponse<QuestionDAO> addQuestion(QuestionDAO questionDAO, String auctionID) throws JsonProcessingException {
        CosmosContainer questions = getContainer();
        String id = "Quest:" + System.currentTimeMillis();
        questionDAO.setId(id);

        CosmosItemResponse<QuestionDAO> res = questions.createItem(questionDAO);

        if (res.getStatusCode() < 300 && CACHE_FLAG) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                ObjectMapper mapper = new ObjectMapper();
                jedis.set("question:" + questionDAO.getId(), mapper.writeValueAsString(questionDAO));

            } catch (Exception e) {
                throw e;
            }
        }
        return res;
    }

    @Override
    public CosmosItemResponse<QuestionDAO> addReply(String reply, QuestionDAO questionDAO) throws JsonProcessingException {
        CosmosContainer questions = getContainer();
        PartitionKey key = new PartitionKey(questionDAO.getId());
        questionDAO.setReply(reply);
        CosmosItemResponse<QuestionDAO> res = questions.replaceItem(questionDAO, questionDAO.getId(), key, new CosmosItemRequestOptions());

        if (res.getStatusCode() < 300) {
            try (Jedis jedis = RedisCache.getCachePool().getResource()) {
                ObjectMapper mapper = new ObjectMapper();
                jedis.set("question:" + questionDAO.getId(), mapper.writeValueAsString(questionDAO));
            } catch (Exception e) {
                throw e;
            }
        }
        return res;
    }

    @Override
    public QuestionDAO getQuestionById(String id) throws JsonProcessingException {
        QuestionDAO a = null;
        if (CACHE_FLAG) a = CachePlus.getQuestion(id);
        if (a == null) {
            CosmosContainer questions = getContainer();
            a = questions.queryItems("SELECT * FROM questions WHERE questions.id=\"" + id + "\"", new CosmosQueryRequestOptions(), QuestionDAO.class).stream().findFirst().get();
        }
        if (a == null) throw new NotFoundException();
        return a;
    }
}
