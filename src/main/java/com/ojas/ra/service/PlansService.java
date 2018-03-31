package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.ojas.ra.domain.Plans;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface PlansService {

	public boolean createPlans(Plans plans) throws RAException;

	public boolean updatePlans(Plans plans) throws RAException;

	public boolean deletePlansById(ObjectId ObjId) throws RAException;

	public Plans getPlansByPrimaryId(Map<String,Object> condition) throws RAException;

	public List<Plans> advancedFind(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page, int size);

	public List<Plans> getAllPlans(MongoSortVO sort, int page, int size);
}
