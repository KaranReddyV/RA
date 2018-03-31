package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.Resource;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface ResourceService {

	public List<Resource> advancedFindByCondition(Map<String, MongoAdvancedQuery> resourceMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize);

	public List<Resource> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	public Resource findOneByCondition(Map<String, Object> condition);

	public boolean createResource(Resource resource);

	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);

	public boolean saveResource(Resource resource);

	public List<Resource> getByMapObjects(MongoSortVO sort, int pageNo, int pageSize, Map<String, Object> condition);


}
