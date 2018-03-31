package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.PlansDAO;
import com.ojas.ra.domain.Plans;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.PlansService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;
import com.ojas.ra.util.MongoUtil;

public class PlansServiceImpl implements PlansService {

	@Autowired
	PlansDAO plansDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public boolean createPlans(Plans plans) throws RAException {
		boolean b;
		try {
			DB db = initializeDB();

			plansDAO.setPojo(new Plans());
			plansDAO.getCollection("plans", db);
			b = plansDAO.insert(plans);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean updatePlans(Plans plans) throws RAException {
		boolean b;
		try {

			DB db = initializeDB();
			plansDAO.getCollection("plans", db);
			plansDAO.setPojo(new Plans());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", plans.get_id());
			b = plansDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(plans));
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean deletePlansById(ObjectId ObjId) throws RAException {
		boolean b;
		try {

			DB db = initializeDB();
			plansDAO.getCollection("plans", db);

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", ObjId);

			b = plansDAO.removeByCondition(condition);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public Plans getPlansByPrimaryId(Map<String,Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
		plansDAO.setPojo(new Plans());
			plansDAO.getCollection("plans", db);

			return plansDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<Plans> advancedFind(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page, int size) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			plansDAO.getCollection("plans", db);
			plansDAO.setPojo(new Plans());
			List<Plans> list = plansDAO.advancedFindByCondition(condition, sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		plansDAO.setPojo(new Plans());
		return db;

	}

	@Override
	public List<Plans> getAllPlans(MongoSortVO sort, int page, int size) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
	plansDAO.getCollection("plans", db);
			plansDAO.setPojo(new Plans());
			List<Plans> list = plansDAO.getAllObjects(sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
}
