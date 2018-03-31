package com.ojas.ra.service;

import java.util.List;
import java.util.Map;
import com.ojas.ra.domain.JobType;
import com.ojas.ra.util.MongoSortVO;

public interface JobTypeSevice {

	public boolean createJobType(JobType jobType);

	public boolean updateJobType(JobType jobtype);

	public List<JobType> getAllJobType(MongoSortVO sort, int page, int size);

	public JobType getOneByCondition(Map<String, Object> condition);

	public List<JobType> getAllJobType(MongoSortVO sort);

}
