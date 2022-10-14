package main.java.utils;

import java.util.Locale;

import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.util.CosmosPagedIterable;

import main.java.data.*;

/**
 * Standalone program for accessing the database
 *
 */
public class TestUsers
{
	public static void main(String[] args) {

		try {
			Locale.setDefault(Locale.US);
			CosmosDBLayer db = CosmosDBLayer.getInstance();
			String id = "0:" + System.currentTimeMillis();
			CosmosItemResponse<data.UserDAO> res = null;
			data.UserDAO u = new data.UserDAO();
			u.setId(id);
			u.setName("SCC " + id);
			u.setPwd("super_secret");
			u.setPhotoId("0:34253455");
			u.setChannelIds(new String[0]);

			res = db.putUser(u);
			System.out.println( "Put result");
			System.out.println( res.getStatusCode());
			System.out.println( res.getItem());

			System.out.println( "Get for id = " + id);
			CosmosPagedIterable<data.UserDAO> resGet = db.getUserById(id);
			for( data.UserDAO e: resGet) {
				System.out.println( e);
			}

			System.out.println( "Get for all ids");
			resGet = db.getUsers();
			for( data.UserDAO e: resGet) {
				System.out.println( e);
			}

			// Now, let's create and delete
			id = "0:" + System.currentTimeMillis();
			res = null;
			u = new data.UserDAO();
			u.setId(id);
			u.setName("SCC " + id);
			u.setPwd("super_secret");
			u.setPhotoId("0:34253455");
			u.setChannelIds(new String[0]);

			res = db.putUser(u);
			System.out.println( "Put result");
			System.out.println( res.getStatusCode());
			System.out.println( res.getItem());
			System.out.println( "Get for id = " + id);

			System.out.println( "Get by id result");
			resGet = db.getUserById(id);
			for( data.UserDAO e: resGet) {
				System.out.println( e);
			}
			
			System.out.println( "Delte user");
			db.delUserById(id);

			System.out.println( "Get by id result");
			resGet = db.getUserById(id);
			for( data.UserDAO e: resGet) {
				System.out.println( e);
			}

			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


