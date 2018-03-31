package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.RoleMappingDAO;
import com.ojas.ra.domain.RoleMapping;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.RoleMappingService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;

public class RoleMappingServiceImpl implements RoleMappingService {

	@Autowired
	RoleMappingDAO roleMappingDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public boolean createRoleMapping(RoleMapping roleMapping) throws RAException {

		boolean b;
		try {
			DB db = initializeDB();
			roleMappingDAO.getCollection("roleMapping", db);

			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			b = roleMappingDAO.insert(roleMapping);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	@Override
	public RoleMapping findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);

			return roleMappingDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<RoleMapping> findOneRoleMappingByUserId(ObjectId id) throws RAException {
		List<RoleMapping> mappinglist = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);

			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(id);
			Map<String, MongoAdvancedQuery> roleMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			roleMappingcondition.put("user_id", equal);
			mappinglist = roleMappingDAO.advancedFindByCondition(roleMappingcondition, null, 1, 10);
			mongoDBClient.closeMongoClient();

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return mappinglist;
	}

	@Override
	public boolean delete(ObjectId id) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			b = roleMappingDAO.removeByCondition(condition);

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean removeByPrimaryId(String value) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleMappingDAO.setPojo(new RoleMapping());
			roleMappingDAO.getCollection("roleMapping", db);
			return roleMappingDAO.removeByPrimaryId(value);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}

	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		roleMappingDAO.setPojo(new RoleMapping());
		return db;

	}

	@Override
	public void removeByCondition(Map<String, Object> condition) {
		DB db = mongoDBClient.getReadMongoDB();
		roleMappingDAO.setPojo(new RoleMapping());
		roleMappingDAO.getCollection("roleMapping", db);
		roleMappingDAO.removeByCondition(condition);

	}

}
