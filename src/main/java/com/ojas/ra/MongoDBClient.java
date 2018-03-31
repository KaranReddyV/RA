package com.ojas.ra;

import com.mongodb.MongoClient;
import com.ojas.ra.exception.RAException;
import com.mongodb.DB;

public interface MongoDBClient {
	
	
	
	public MongoClient getReadMongoClient() throws RAException;
	
	public MongoClient getWriteMongoClient() throws RAException;;
	
    public DB getReadMongoDB() throws RAException;;
	
	public DB getWriteMongoDB() throws RAException;;
	
	public void closeMongoClient() throws RAException;;

}
