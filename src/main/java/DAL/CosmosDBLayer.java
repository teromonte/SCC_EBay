package main.java.DAL;

import com.azure.cosmos.*;

import java.util.Locale;

public class CosmosDBLayer {
	private static final String CONNECTION_URL = "https://scc22234204.documents.azure.com:443/";
	private static final String DB_KEY = "xfCPprZp1yI5R8qTXR2JlJiELkWJyOcKGfVjsdyi62ONZJoFzY9J9j8lsCVpFLkYU5frwxqnlkSjUhQlQiAvBA==";
	private static final String DB_NAME = "scc2223db";

	public static final String USER_CONTAINER = "users";

	public static final String BID_CONTAINER = "bids";

	public static final String QUESTION_CONTAINER = "questions";

	public static final String AUCTION_CONTAINER = "auctions";

	private static CosmosDBLayer instance;

	public static synchronized CosmosDBLayer getInstance() {
		if( instance != null)
			return instance;

		Locale.setDefault(Locale.US);

		CosmosClient client = new CosmosClientBuilder()
				.endpoint(CONNECTION_URL)
				.key(DB_KEY)
				//.directMode()
				.gatewayMode()
				// replace by .directMode() for better performance
				.consistencyLevel(ConsistencyLevel.SESSION)
				.connectionSharingAcrossClientsEnabled(true)
				.contentResponseOnWriteEnabled(true)
				.buildClient();
		instance = new CosmosDBLayer( client);
		return instance;

	}

	private CosmosClient client;
	private CosmosDatabase db;
	private CosmosContainer contaner;

	private CosmosDBLayer(CosmosClient client) {
		this.client = client;
	}

	public synchronized void init(String containerID) {
		if( db != null)
		if( instance != null)
			return;
		db = client.getDatabase(DB_NAME);
		contaner = db.getContainer(containerID);

	}

	public CosmosContainer getContainer() {
		return contaner;
	}

	public void close() {
		client.close();
	}

}