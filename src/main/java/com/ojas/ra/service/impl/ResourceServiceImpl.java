package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.ResourceDAO;
import com.ojas.ra.domain.Resource;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.ResourceService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceDAO resourceDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public boolean createResource(Resource resource) throws RAException {

		boolean b;
		try {
			DB db = initializeDB();

			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			b = resourceDAO.insert(resource);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	@Override
	public boolean saveResource(Resource resource) {
		boolean b;
		try {
			DB db = initializeDB();
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			b = resourceDAO.save(resource);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public List<Resource> advancedFindByCondition(Map<String, MongoAdvancedQuery> resourceMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);

			return resourceDAO.advancedFindByCondition(resourceMappingcondition, sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<Resource> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {
		List<Resource> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			resourceDAO.getCollection("resource", db);
			resourceDAO.setPojo(new Resource());
			list = resourceDAO.getAllObjects(sort, page, size);
			mongoDBClient.closeMongoClient();
			
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public Resource findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);

			return resourceDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		resourceDAO.setPojo(new Resource());
		return db;

	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);

			return resourceDAO.updateMapByCondition(condition, target);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<Resource> getByMapObjects(MongoSortVO sort, int pageNo, int pageSize, Map<String, Object> condition) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			resourceDAO.setPojo(new Resource());
			resourceDAO.getCollection("resource", db);
			String mappedPojo = "resourceMapping";

			return resourceDAO.getByMapObjects(sort, pageNo, pageSize, mappedPojo, condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

}
