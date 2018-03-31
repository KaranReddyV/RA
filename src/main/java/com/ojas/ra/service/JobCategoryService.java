package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.JobCategory;
import com.ojas.ra.util.MongoSortVO;

public interface JobCategoryService {
	public boolean createJobCategory(JobCategory jobCategory);

	public boolean updateJobCategory(JobCategory jobCategory);

	public List<JobCategory> getAllJobCategory(MongoSortVO sort, int page, int size);

	public JobCategory getOneByCondition(Map<String, Object> condition);
	
	public  List<JobCategory> getAllJobCategory(MongoSortVO sort);
	

}
