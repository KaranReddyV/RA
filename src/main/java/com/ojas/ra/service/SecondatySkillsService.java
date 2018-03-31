package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.SecondarySkills;
import com.ojas.ra.util.MongoSortVO;

public interface SecondatySkillsService {
	
	public boolean create(SecondarySkills secondarySkills);

	public boolean update(SecondarySkills secondarySkills);

	public List<SecondarySkills> getAll(MongoSortVO sort, int page, int size);

	public SecondarySkills getOneByCondition(Map<String, Object> condition);

	public List<SecondarySkills> getAll(MongoSortVO sort);
	
	

}
