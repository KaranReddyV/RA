package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.ojas.ra.domain.Registration;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface RegistrationService {

	public boolean create(Registration registration) throws RAException;

	public boolean save(Registration registration) throws RAException;

	public boolean delete(ObjectId id) throws RAException;

	public List<Registration> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	public boolean removeByPrimaryId(String id);

	public Registration findOneByCondition(Map<String, Object> condition);

	public List<Registration> advancedFindByCondition(Map<String, MongoAdvancedQuery> registrationMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize);

	public List<Registration> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException;

	Registration findOneByPrimaryId(String value) throws RAException;

	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);

	public List<Registration> getAllObjects(MongoSortVO sort);

}
