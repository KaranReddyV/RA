package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.RegistrationDAO;
import com.ojas.ra.domain.Registration;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.RegistrationService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	RegistrationDAO registrationDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	@Override
	public boolean create(Registration registration) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			b = registrationDAO.insert(registration);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean save(Registration registration) throws RAException {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			b = registrationDAO.save(registration);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public boolean delete(ObjectId id) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			b = registrationDAO.removeByCondition(condition);
			mongoDBClient.closeMongoClient();

		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Registration> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			return registrationDAO.findAllByCondition(condition, sort);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public boolean removeByPrimaryId(String value) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			return registrationDAO.removeByPrimaryId(value);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Registration findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			return registrationDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Registration findOneByPrimaryId(String value) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			return registrationDAO.findOneByPrimaryId(value);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<Registration> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.getCollection("registration", db);
			registrationDAO.setPojo(new Registration());
			List<Registration> list = registrationDAO.getAllObjects(sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<Registration> getAllObjects(MongoSortVO sort) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.getCollection("registration", db);
			registrationDAO.setPojo(new Registration());
			List<Registration> list = registrationDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		registrationDAO.setPojo(new Registration());
		return db;

	}

	@Override
	public List<Registration> advancedFindByCondition(Map<String, MongoAdvancedQuery> requementMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			return registrationDAO.advancedFindByCondition(requementMappingcondition, sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationDAO.setPojo(new Registration());
			registrationDAO.getCollection("registration", db);

			return registrationDAO.updateMapByCondition(condition, target);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

}