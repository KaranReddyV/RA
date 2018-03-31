package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.UserAccountStatus;
import com.ojas.ra.dao.AccessTokenDAO;
import com.ojas.ra.dao.RoleDAO;
import com.ojas.ra.dao.RoleMappingDAO;
import com.ojas.ra.dao.UserAccountDAO;
import com.ojas.ra.domain.AccessToken;
import com.ojas.ra.domain.Registration;
import com.ojas.ra.domain.Requirement;
import com.ojas.ra.domain.Role;
import com.ojas.ra.domain.UserAccount;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.UserService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

public class UserServiceImpl implements UserService {

	@Autowired
	UserAccountDAO userAccountDAO;

	@Autowired
	AccessTokenDAO accessTokenDAO;

	@Autowired
	RoleMappingDAO roleMappingDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	public Role getRole(String role) {

		return null;

	}

	public boolean create(UserAccount user) throws RAException {

		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();

			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			user.setStatus(UserAccountStatus.STATUS_DISABLED.name());
			b = userAccountDAO.insert(user);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	public void save(UserAccount user) throws RAException {
		try {

			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			userAccountDAO.save(user);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public boolean delete(ObjectId id) throws RAException {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);

			b = userAccountDAO.removeByCondition(condition);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	public UserAccount getByUsername(String username) throws RAException {
		UserAccount userAccount = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();

			Map<String, Object> condition = new HashMap<String, Object>();
			userAccountDAO.getCollection("userAccount", db);
			userAccountDAO.setPojo(new UserAccount());

			condition.put("username", username);

			userAccount = userAccountDAO.findOneByCondition(condition);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return userAccount;
	}

	public AccessToken createAccessToken(UserAccount user) throws RAException {
		AccessToken accessToken = null;
		try {
			DB db = mongoDBClient.getWriteMongoDB();

			accessToken = new AccessToken(user.getUsername(), UUID.randomUUID().toString());
			accessTokenDAO.setPojo(accessToken);
			accessTokenDAO.getCollection("accessToken", db);
			accessTokenDAO.insert(accessToken);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return accessToken;
	}

	@Override
	public UserAccount loadUserByUsername(String arg0) throws RAException {
		UserAccount userAccount = null;
		try {
			DB db = mongoDBClient.getReadMongoDB();

			userAccountDAO.setPojo(new UserAccount());
			Map<String, Object> condition = new HashMap<String, Object>();
			userAccountDAO.getCollection("userAccount", db);
			userAccountDAO.setPojo(new UserAccount());

			condition.put("username", arg0);

			userAccount = userAccountDAO.findOneByCondition(condition);

			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return userAccount;
	}

	@SuppressWarnings("deprecation")
	@Override
	public UserAccount getUserByToken(String arg0) throws RAException {
		UserAccount userAccount = null;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("token", arg0);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("expiry");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			DB db = mongoDBClient.getWriteMongoDB();
			accessTokenDAO.setPojo(new AccessToken());
			accessTokenDAO.getCollection("accessToken", db);
			String userName = null;

			List<AccessToken> list = accessTokenDAO.findAllByCondition(condition, sort);
			if (null != list && 0 < list.size()) {
				for (AccessToken pojo : list) {
					userName = pojo.getUserName();
					break;
				}
			}

			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			Map<String, Object> usercondition = new HashMap<String, Object>();
			usercondition.put("username", userName);
			userAccount = userAccountDAO.findOneByCondition(usercondition);

			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return userAccount;
	}

	public boolean removeByPrimaryId(ObjectId value) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			b= userAccountDAO.removeByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	public List<UserAccount> findByRegistrationId(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort,
			int pageNo, int pageSize) throws RAException {
		try {

			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			return userAccountDAO.advancedFindByCondition(condition, sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}

	}

	public UserAccount findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			return userAccountDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<UserAccount> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);
			return userAccountDAO.getAllObjects(sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
		}
		return null;
	}

	@Override
	public List<Role> getAllRoles(MongoSortVO sort, int pageNo, int pageSize) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			roleDAO.setPojo(new Role());
			roleDAO.getCollection("role", db);
			return roleDAO.getAllObjects(sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}

	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			return userAccountDAO.updateMapByCondition(condition, target);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
	
	@Override
	public List<UserAccount> advancedFindByCondition(Map<String, MongoAdvancedQuery> requementMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			userAccountDAO.setPojo(new UserAccount());
			userAccountDAO.getCollection("userAccount", db);

			return userAccountDAO.advancedFindByCondition(requementMappingcondition, sort, pageNo, pageSize);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
}
