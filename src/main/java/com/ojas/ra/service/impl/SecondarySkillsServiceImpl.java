package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
  import com.ojas.ra.dao.SecondarySkillsDAO;
import com.ojas.ra.domain.SecondarySkills;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.SecondatySkillsService;
import com.ojas.ra.util.MongoSortVO;

public class SecondarySkillsServiceImpl implements SecondatySkillsService {
	@Autowired
	SecondarySkillsDAO secondarySkillsDAO;
	@Autowired
	MongoDBClient mongoDBClient;
	
	
		
	@Override
	public boolean create(SecondarySkills secondarySkills) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			secondarySkillsDAO.setPojo(new SecondarySkills());
			secondarySkillsDAO.getCollection("secondarySkills", db);

			b = secondarySkillsDAO.insert(secondarySkills);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean update(SecondarySkills secondarySkills) {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			secondarySkillsDAO.setPojo(new SecondarySkills());
			secondarySkillsDAO.getCollection("secondarySkills", db);

			b = secondarySkillsDAO.save(secondarySkills);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public List<SecondarySkills> getAll(MongoSortVO sort, int page, int size) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			secondarySkillsDAO.getCollection("secondarySkills", db);
			secondarySkillsDAO.setPojo(new SecondarySkills());
			List<SecondarySkills> list = secondarySkillsDAO.getAllObjects(sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public SecondarySkills getOneByCondition(Map<String, Object> condition) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			secondarySkillsDAO.setPojo(new SecondarySkills());
			secondarySkillsDAO.getCollection("secondarySkills", db);

			return secondarySkillsDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public List<SecondarySkills> getAll(MongoSortVO sort) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			secondarySkillsDAO.getCollection("secondarySkills", db);
			secondarySkillsDAO.setPojo(new SecondarySkills());
			List<SecondarySkills> list =secondarySkillsDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
	}


	


