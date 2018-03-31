package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.RequirementDAO;
import com.ojas.ra.domain.Requirement;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.RequirementService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;
import com.ojas.ra.util.MongoUtil;

public class RequirementServiceImpl implements RequirementService {

	@Autowired
	RequirementDAO requirementDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public boolean createRequirement(Requirement requirement) throws RAException {

		boolean b;
		try {
			DB db = initializeDB();

			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);
			b = requirementDAO.insert(requirement);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean saveRequirement(Requirement requirement) throws RAException {
		boolean b;
		try {

			DB db = initializeDB();
			requirementDAO.getCollection("requirement", db);
			requirementDAO.setPojo(new Requirement());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", requirement.get_id());
			b = requirementDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(requirement));
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean deleteRequirement(ObjectId objId) throws RAException {
		boolean b;
		try {

			DB db = initializeDB();
			requirementDAO.getCollection("requirement", db);

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", objId);

			b = requirementDAO.removeByCondition(condition);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Requirement> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);

			return requirementDAO.findAllByCondition(condition, sort);

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public boolean removeByPrimaryId(String value) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);

			return requirementDAO.removeByPrimaryId(value);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Requirement findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);

			return requirementDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Requirement findOneByPrimaryId(String value) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);

			return requirementDAO.findOneByPrimaryId(value);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<Requirement> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.getCollection("requirement", db);
			requirementDAO.setPojo(new Requirement());
			List<Requirement> list = requirementDAO.getAllObjects(sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		requirementDAO.setPojo(new Requirement());
		return db;

	}

	@Override
	public List<Requirement> advancedFindByCondition(Map<String, MongoAdvancedQuery> requementMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);

			return requirementDAO.advancedFindByCondition(requementMappingcondition, sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Document findOneByTextSearch(String text, boolean caseSensitive, boolean diacriticSensitive)
			throws RAException {
		Document article = null;
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("spring_security_mongodb");
		MongoCollection<Document> collection = database.getCollection("requirement");
		try {

			collection.createIndex(new BasicDBObject("jobCategory", 1));
			collection.createIndex(new BasicDBObject("jobType", 1));
			collection.createIndex(new BasicDBObject("experience", 1));
			collection.createIndex(new BasicDBObject("description", 1));

			MongoCursor<Document> cursor = null;
			cursor = collection.find(new Document("$text",
					new Document("$search", text).append("$caseSensitive", new Boolean(caseSensitive))
							.append("$diacriticSensitive", new Boolean(diacriticSensitive))))
					.iterator();
			// System.out.println(collection);
			while (cursor.hasNext()) {
				article = cursor.next();
				System.out.println(article);
			}

			cursor.close();
		} catch (Exception e) {
			mongoDBClient.closeMongoClient();
			e.printStackTrace();
			mongoDBClient.closeMongoClient();
		} finally {
			mongoClient.close();
		}
		return article;

	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.setPojo(new Requirement());
			requirementDAO.getCollection("requirement", db);

			return requirementDAO.updateMapByCondition(condition, target);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}

	}

	@Override
	public List<Requirement> getAllObjects(MongoSortVO sort) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			requirementDAO.getCollection("requirement", db);
			requirementDAO.setPojo(new Requirement());
			List<Requirement> list = requirementDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

}
