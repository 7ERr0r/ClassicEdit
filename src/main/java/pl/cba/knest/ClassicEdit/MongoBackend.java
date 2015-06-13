package pl.cba.knest.ClassicEdit;

import java.net.UnknownHostException;



import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoBackend extends Backend {

	private MongoClient mongoClient;
	private String databaseName;
	private DB database;

	
	public MongoBackend(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		this.databaseName = databaseName;
		database = mongoClient.getDB(databaseName);
	}

	public static MongoBackend create(String host, int port, String databaseName) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(host, port);
		return new MongoBackend(mongoClient, databaseName);
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

	public void close() {
		
	}

	@Override
	public void save(String name) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void load() {
		throw new RuntimeException("Not implemented yet");
	}
	
	
	
}
