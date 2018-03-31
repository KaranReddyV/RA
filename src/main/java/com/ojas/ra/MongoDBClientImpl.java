package com.ojas.ra;

import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.ojas.ra.exception.RAException;

public class MongoDBClientImpl implements MongoDBClient {

	MongoConfig mongoConfig;

	MongoClient mongoClient;

	MongoDBClientImpl(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	/**
	 * 
	 */

	@SuppressWarnings("deprecation")
	public MongoClient getReadMongoClient() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));

			mongoClient.setReadPreference(ReadPreference.primaryPreferred());

		} catch (Exception e) {
			throw new RAException("Error occur During the getting connection");
		}

		return mongoClient;

	}

	@SuppressWarnings("deprecation")
	public MongoClient getWriteMongoClient() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));
			mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
		} catch (Exception e) {
			throw new RAException("Error occur During the getting connection");
		}

		return mongoClient;

	}

	@SuppressWarnings("deprecation")
	@Override
	public DB getReadMongoDB() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));

			mongoClient.setReadPreference(ReadPreference.primaryPreferred());
		} catch (Exception e) {
			throw new RAException("Error occur During the getting connection");
		}

		return mongoClient.getDB(mongoConfig.getDatabase());
	}

	@Override
	public DB getWriteMongoDB() throws RAException {
		try {
			mongoClient = new MongoClient(
					Arrays.asList(new ServerAddress(mongoConfig.getHost(), Integer.parseInt(mongoConfig.getPort()))));

			mongoClient.setReadPreference(ReadPreference.secondaryPreferred());
		} catch (Exception e) {
			throw new RAException("Error occur During the getting connection");
		}

		return mongoClient.getDB(mongoConfig.getDatabase());
	}

	@Override
	public void closeMongoClient() throws RAException {
		try {

			mongoClient.close();
		} catch (Exception e) {
			throw new RAException("Error occur During the closing connection");
		}

	}

}
