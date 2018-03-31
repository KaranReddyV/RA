package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.ResourceDAO;
import com.ojas.ra.dao.ResourceMappingDAO;
import com.ojas.ra.domain.ResourceMapping;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.ResourceMappingService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;
import com.ojas.ra.util.MongoUtil;

public class ResourceMappingServiceImpl implements ResourceMappingService {
	@Autowired
	ResourceMappingDAO resourceMappingDAO;
	@Autowired
	ResourceDAO resourceDAO;
	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public boolean createResourceMapping(ResourceMapping resourceMapping) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();
			resourceMappingDAO.getCollection("resourceMapping", db);

			resourceMappingDAO.setPojo(new ResourceMapping());
			resourceMappingDAO.getCollection("resourceMapping", db);
			b = resourceMappingDAO.insert(resourceMapping);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	@Override
	public boolean deleteResourceMapping(ObjectId objId) {
		boolean b;
		try {

			DB db = initializeDB();
			resourceMappingDAO.getCollection("resourceMapping", db);

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", objId);

			b = resourceMappingDAO.removeByCondition(condition);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	@Override
	public boolean updateResourceMapping(ResourceMapping resourceMapping) {
		boolean b;
		try {

			DB db = initializeDB();
			resourceMappingDAO.getCollection("resourceMapping", db);
			resourceMappingDAO.setPojo(new ResourceMapping());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", resourceMapping.get_id());
			b = resourceMappingDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(resourceMapping));
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		resourceMappingDAO.setPojo(new ResourceMapping());
		return db;

	}

	@Override
	public List<ResourceMapping> advancedFind(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int pageNo,
			int pageSize) {
		{
			try {
				DB db = mongoDBClient.getReadMongoDB();
				resourceMappingDAO.setPojo(new ResourceMapping());
				resourceMappingDAO.getCollection("resourceMapping", db);

				return resourceMappingDAO.advancedFindByCondition(condition, sort, pageNo, pageSize);
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();

				throw new RAException(e.getMessage());
			}
		}

	}

}
