package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.JobCategoryDAO;
import com.ojas.ra.domain.JobCategory;
import com.ojas.ra.domain.RegistrationType;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.JobCategoryService;
import com.ojas.ra.util.MongoSortVO;

public class JobCategoryServiceImpl implements JobCategoryService {
	@Autowired
	JobCategoryDAO jobCategoryDAO;
	@Autowired
	MongoDBClient mongoDBClient;

	@Override
	public boolean createJobCategory(JobCategory jobCategory) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobCategoryDAO.setPojo(new JobCategory());
			jobCategoryDAO.getCollection("jobCategory", db);

			b = jobCategoryDAO.insert(jobCategory);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean updateJobCategory(JobCategory jobCategory) {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			jobCategoryDAO.setPojo(new JobCategory());
			jobCategoryDAO.getCollection("jobCategory", db);

			b=jobCategoryDAO.save(jobCategory);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public List<JobCategory> getAllJobCategory(MongoSortVO sort, int page, int size) {
		List<JobCategory> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobCategoryDAO.getCollection("jobCategory", db);
			jobCategoryDAO.setPojo(new JobCategory());
			list= jobCategoryDAO.getAllObjects(sort, page, size);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return list;
	}
	@Override
	public JobCategory getOneByCondition(Map<String, Object> condition) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobCategoryDAO.setPojo(new JobCategory());
			jobCategoryDAO.getCollection("jobCategory", db);

			return jobCategoryDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
	public  List<JobCategory> getAllJobCategory(MongoSortVO sort){
		try {
			DB db = mongoDBClient.getReadMongoDB();
			jobCategoryDAO.getCollection("jobCategory", db);
			jobCategoryDAO.setPojo(new JobCategory());
			List<JobCategory> list = jobCategoryDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
}
}