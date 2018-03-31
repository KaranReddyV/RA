package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.Skills;
import com.ojas.ra.util.MongoSortVO;

public interface SkillsService {

	public List<Skills> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	public Skills findOneByCondition(Map<String, Object> condition);

}
