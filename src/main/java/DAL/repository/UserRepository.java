package main.java.DAL.repository;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.DAL.CosmosDBLayer;
import main.java.DAL.gateway.IUserGateway;
import main.java.models.DAO.UserDAO;
import main.java.models.entities.User;
import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import main.java.models.DAO.UserDAO;

import java.util.Locale;

public class UserRepository implements IUserGateway {

    public CosmosPagedIterable<UserDAO> getUserById(String id) {
        init();
        return users.queryItems("SELECT * FROM users WHERE users.id=\"" + id + "\"", new CosmosQueryRequestOptions(), UserDAO.class);
    }

    public void test() {
        try {
            Locale.setDefault(Locale.US);
            CosmosDBLayer db = CosmosDBLayer.getInstance();
            String id = "0:" + System.currentTimeMillis();
            CosmosItemResponse<UserDAO> res = null;
            UserDAO u = new UserDAO();
            u.setId(id);
            u.setName("SCC " + id);
            u.setPwd("super_secret");
            u.setPhotoId("0:34253455");
            u.setChannelIds(new String[0]);

            res = db.putUser(u);
            System.out.println("Put result");
            System.out.println(res.getStatusCode());
            System.out.println(res.getItem());

            System.out.println("Get for id = " + id);
            CosmosPagedIterable<UserDAO> resGet = db.getUserById(id);
            for (UserDAO e : resGet) {
                System.out.println(e);
            }

            System.out.println("Get for all ids");
            resGet = db.getUsers();
            for (UserDAO e : resGet) {
                System.out.println(e);
            }

            // Now, let's create and delete
            id = "0:" + System.currentTimeMillis();
            res = null;
            u = new UserDAO();
            u.setId(id);
            u.setName("SCC " + id);
            u.setPwd("super_secret");
            u.setPhotoId("0:34253455");
            u.setChannelIds(new String[0]);

            res = db.putUser(u);
            System.out.println("Put result");
            System.out.println(res.getStatusCode());
            System.out.println(res.getItem());
            System.out.println("Get for id = " + id);

            System.out.println("Get by id result");
            resGet = db.getUserById(id);
            for (UserDAO e : resGet) {
                System.out.println(e);
            }

            System.out.println("Delte user");
            db.delUserById(id);

            System.out.println("Get by id result");
            resGet = db.getUserById(id);
            for (UserDAO e : resGet) {
                System.out.println(e);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {

    }
}
