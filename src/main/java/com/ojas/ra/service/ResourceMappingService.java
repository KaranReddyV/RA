package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.ojas.ra.domain.ResourceMapping;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface ResourceMappingService {

	public boolean createResourceMapping(ResourceMapping rm);

	public boolean deleteResourceMapping(ObjectId objId);

	public boolean updateResourceMapping(ResourceMapping rm);

	public List<ResourceMapping> advancedFind(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page,
			int size);

}
