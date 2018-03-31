package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.RoleDAO;
import com.ojas.ra.domain.Role;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.RoleService;
import com.ojas.ra.util.MongoSortVO;

public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public boolean createRole(Role role) throws RAException {

		boolean b;
		try {
			DB db = initializeDB();
			roleDAO.getCollection("role", db);

			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);
			b = roleDAO.insert(role);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;

	}

	@Override
	public Role findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);

			return roleDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		roleDAO.setPojo(new Role());
		return db;

	}

	/*
	 * @Override public List<Role> aggregationSearch(Map<String, Object>
	 * condition) throws RAException { try { DB db =
	 * mongoDBClient.getReadMongoDB(); roleDAO.setPojo(new Role());
	 * roleDAO.getCollection("role", db);
	 * 
	 * Map<String, Object> lookupcondition = new HashMap<String, Object>();
	 * lookupcondition.put("from", "roleMapping");
	 * lookupcondition.put("localField", "role_id");
	 * lookupcondition.put("foreignField", "_id"); lookupcondition.put("as",
	 * "role");
	 * 
	 * return roleDAO.aggregationSearch(condition,lookupcondition); } catch
	 * (RAException e) { mongoDBClient.closeMongoClient();
	 * 
	 * throw new RAException(e.getMessage()); } }
	 */
	public List<Role> getByMapObjects(MongoSortVO sort, int page, int size, Map<String, Object> mapCondition)
			throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);

			String mappedPojo = "roleMapping";
			return roleDAO.getByMapObjects(sort, page, size, mappedPojo, mapCondition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}

	}

}
