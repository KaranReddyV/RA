package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.PrimarySkillsDAO;
import com.ojas.ra.domain.PrimarySkills;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.PrimarySkillsService;
import com.ojas.ra.util.MongoSortVO;

public class PrimarySkillsServiceImpl implements PrimarySkillsService {
	@Autowired
	PrimarySkillsDAO primarySkillsDAO;
	@Autowired
	MongoDBClient mongoDBClient;
	@Override
	public boolean create(PrimarySkills primarySkills) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			primarySkillsDAO.setPojo(new PrimarySkills());
			primarySkillsDAO.getCollection("primarySkills", db);

			b = primarySkillsDAO.insert(primarySkills);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
		
	}

	@Override
	public boolean update(PrimarySkills primarySkills) {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			primarySkillsDAO.setPojo(new PrimarySkills());
			primarySkillsDAO.getCollection("primarySkills", db);

			b = primarySkillsDAO.save(primarySkills);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public List<PrimarySkills> getAll(MongoSortVO sort, int page, int size) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			primarySkillsDAO.getCollection("primarySkills", db);
			primarySkillsDAO.setPojo(new PrimarySkills());
			List<PrimarySkills> list = primarySkillsDAO.getAllObjects(sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public PrimarySkills getOneByCondition(Map<String, Object> condition) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			primarySkillsDAO.setPojo(new PrimarySkills());
			primarySkillsDAO.getCollection("primarySkills", db);

			return primarySkillsDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	
	}

	@Override
	public List<PrimarySkills> getAll(MongoSortVO sort) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			primarySkillsDAO.getCollection("primarySkills", db);
			primarySkillsDAO.setPojo(new PrimarySkills());
			List<PrimarySkills> list =primarySkillsDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
	}


