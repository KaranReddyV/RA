package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.JobTypeDAO;
import com.ojas.ra.domain.JobType;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.JobTypeSevice;
import com.ojas.ra.util.MongoSortVO;

public class JobTypeServiceImpl implements JobTypeSevice {

	@Autowired
	JobTypeDAO jobTypeDAO;
	@Autowired
	MongoDBClient mongoDBClient;

	@Override
	public boolean createJobType(JobType jobType) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobTypeDAO.setPojo(new JobType());
			jobTypeDAO.getCollection("jobType", db);

			b = jobTypeDAO.insert(jobType);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean updateJobType(JobType jobType) {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			jobTypeDAO.setPojo(new JobType());
			jobTypeDAO.getCollection("jobType", db);

			b = jobTypeDAO.save(jobType);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public List<JobType> getAllJobType(MongoSortVO sort, int page, int size) {
		List<JobType> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobTypeDAO.getCollection("jobType", db);
			jobTypeDAO.setPojo(new JobType());
			list = jobTypeDAO.getAllObjects(sort, page, size);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public JobType getOneByCondition(Map<String, Object> condition) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobTypeDAO.setPojo(new JobType());
			jobTypeDAO.getCollection("jobType", db);

			return jobTypeDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
	public  List<JobType> getAllJobType(MongoSortVO sort){
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobTypeDAO.getCollection("jobType", db);
			jobTypeDAO.setPojo(new JobType());
			List<JobType> list = jobTypeDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}


}
