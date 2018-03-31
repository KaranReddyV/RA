package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.SkillsDAO;
import com.ojas.ra.domain.Skills;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.SkillsService;
import com.ojas.ra.util.MongoSortVO;

public class SkillsServiceImpl implements SkillsService {

	@Autowired
	SkillsDAO skillsDAO;

	@Autowired
	private MongoDBClient mongoDBClient;

	@Override
	public List<Skills> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {
		List<Skills> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			skillsDAO.getCollection("skills", db);
			skillsDAO.setPojo(new Skills());
			list= skillsDAO.getAllObjects(sort, page, size);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public Skills findOneByCondition(Map<String, Object> condition) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			skillsDAO.setPojo(new Skills());
			skillsDAO.getCollection("skills", db);

			return skillsDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

}
