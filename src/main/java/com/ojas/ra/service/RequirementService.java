package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.ojas.ra.domain.Requirement;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface RequirementService {

	public boolean createRequirement(Requirement requirement) throws RAException;

	public boolean saveRequirement(Requirement requirement) throws RAException;

	public boolean deleteRequirement(ObjectId requirement) throws RAException;

	public boolean removeByPrimaryId(String value) throws RAException;

	public List<Requirement> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException;

	public Requirement findOneByCondition(Map<String, Object> condition) throws RAException;

	public Requirement findOneByPrimaryId(String value) throws RAException;

	List<Requirement> getAllObjects(MongoSortVO sort, int page, int size) throws RAException;

	public List<Requirement> advancedFindByCondition(Map<String, MongoAdvancedQuery> requementMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize);

	public Document findOneByTextSearch(String text, boolean caseSensitive, boolean diacriticSensitive)
			throws RAException;

	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);

	public List<Requirement> getAllObjects(MongoSortVO sort);

}
