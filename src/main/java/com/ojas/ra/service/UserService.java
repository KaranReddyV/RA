package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.ojas.ra.domain.AccessToken;
import com.ojas.ra.domain.Requirement;
import com.ojas.ra.domain.Role;
import com.ojas.ra.domain.UserAccount;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface UserService {

	public Role getRole(String role) throws RAException;

	public boolean create(UserAccount user) throws RAException;

	public void save(UserAccount user) throws RAException;

	public UserAccount getByUsername(String username) throws RAException;

	public AccessToken createAccessToken(UserAccount user) throws RAException;

	public UserAccount loadUserByUsername(String arg0) throws RAException;

	public UserAccount getUserByToken(String arg0) throws RAException;

	public UserAccount findOneByCondition(Map<String, Object> condition);
	
	public List<UserAccount> findByRegistrationId(Map<String, MongoAdvancedQuery> condition,
			MongoSortVO sort, int pageNo, int pageSize) throws RAException;

	public List<UserAccount> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	
	public List<Role> getAllRoles(MongoSortVO sort, int pageNo, int pageSize);

	public boolean delete(ObjectId id);

	public boolean updateMapByCondition(Map<String, Object> condition1, Map<String, Object> target1);

	public List<UserAccount> advancedFindByCondition(Map<String, MongoAdvancedQuery> requirementMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize);
}
