package local.test.util;

import java.net.UnknownHostException;

import local.test.util.Config;

import org.bson.types.ObjectId;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * @author hernan
 * @version 1.0
 * 
 */
public class MongoClientExt {

	private static MongoClient mongoClient = null;
	private static DB database = null;
	private static DBCollection collection = null;

	/*
	 * Constructors
	 */

	/**
	 * 
	 * @throws UnknownHostException
	 */
	public MongoClientExt() throws UnknownHostException {
		Config conf = new Config("config.properties");
		mongoClient = new MongoClient(conf.getProperty("mongo_host"),
				Integer.parseInt(conf.getProperty("mongo_port")));
	}

	/**
	 * 
	 * @param host Mongo server hostname or ip
	 * @throws UnknownHostException
	 */
	public MongoClientExt(String host) throws UnknownHostException {
		mongoClient = new MongoClient(host);
	}

	/**
	 * 
	 * @param host Mongo server hostname or ip
	 * @param port Mongo server port
	 * @throws UnknownHostException
	 */
	public MongoClientExt(String host, int port) throws UnknownHostException {
		mongoClient = new MongoClient(host, port);
	}
	
	/**
	 * 
	 * @param hostname Mongo server hostname
	 * @param dbName Mongo database name
	 * @param colName Mongo collection name
	 * @throws UnknownHostException
	 */
	public MongoClientExt(String hostname, String dbName, String colName) throws UnknownHostException {
		mongoClient = new MongoClient(hostname);
		database = mongoClient.getDB(dbName);
		collection = database.getCollection(colName);
	}

	/*
	 * Setters
	 */

	public void setDB(String dbname) throws Exception {
		if (mongoClient != null) {
			database = mongoClient.getDB(dbname);
		} else {
			throw new Exception("mongodb.MongoClient is undefined");
		}
	}

	public void setCollection(String collectionName) throws Exception {
		if (database != null) {
			collection = database.getCollection(collectionName);
		} else {
			throw new Exception("mongodb.DB is undefined");
		}
	}

	/*
	 * Getters
	 */

	public DB getDB() {
		return database;
	}
	
	public DBCollection getCollection() {
		return collection;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}
	
	/*
	 * Common Queries
	 */

	public String getValByKey(String key, String keyVal, String retVal) {
		Object keyValue;
		if (key == "_id") {
			keyValue = new ObjectId(keyVal);
		} else {
			keyValue = keyVal;
		}
		BasicDBObject query = new BasicDBObject(key, keyValue);
		DBObject o = collection.findOne(query);
		return o.toMap().get(retVal).toString();
	}

	public DBCursor getRows(String key, String val) {
		Object value;
		if (key == "_id") {
			value = new ObjectId(val);
		} else {
			value = val;
		}
		BasicDBObject query = new BasicDBObject(key, value);
		DBCursor cursor = collection.find(query);
		return cursor;
	}

}
