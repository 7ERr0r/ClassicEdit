package pl.cba.knest.ClassicEdit;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Backend {

	private MongoClient mongoClient;
	private String databaseName;
	private DB database;
	private DBCollection creations;
	
	public Backend(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		this.databaseName = databaseName;
		database = mongoClient.getDB(databaseName);
		creations = database.getCollection("creations");
	}

	public static Backend create(String host, int port, String databaseName) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(host, port);
		return new Backend(mongoClient, databaseName);
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public DB getDatabase() {
		return database;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}

	public DBCollection getCreations() {
		return creations;
	}
	
	
	
}
