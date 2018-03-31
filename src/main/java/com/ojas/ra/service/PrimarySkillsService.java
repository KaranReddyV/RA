package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.PrimarySkills;
import com.ojas.ra.util.MongoSortVO;

public interface PrimarySkillsService {
	
public boolean create(PrimarySkills primarySkills);

public boolean update(PrimarySkills primarySkills);

public List<PrimarySkills> getAll(MongoSortVO sort, int page, int size);

public PrimarySkills getOneByCondition(Map<String, Object> condition);

public List<PrimarySkills> getAll(MongoSortVO sort);
	

}
