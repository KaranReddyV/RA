package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.JoinWithInDAO;
import com.ojas.ra.domain.JoinWithIn;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.JoinWithInService;
import com.ojas.ra.util.MongoSortVO;

public class JoinWithInServiceImpl implements JoinWithInService {
	@Autowired
	JoinWithInDAO joinWithInDAO;
	@Autowired
	MongoDBClient mongoDBClient;

	@Override
	public boolean createJoinWithIn(JoinWithIn joinWithIn) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			joinWithInDAO.setPojo(new JoinWithIn());
			joinWithInDAO.getCollection("joinWithIn", db);

			b = joinWithInDAO.insert(joinWithIn);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean updateJoinWithIn(JoinWithIn joinWithIn) {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			joinWithInDAO.setPojo(new JoinWithIn());
			joinWithInDAO.getCollection("joinWithIn", db);

			b = joinWithInDAO.save(joinWithIn);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public List<JoinWithIn> getAllJoinWithIn(MongoSortVO sort, int page, int size) {
		List<JoinWithIn> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			joinWithInDAO.getCollection("joinWithIn", db);
			joinWithInDAO.setPojo(new JoinWithIn());
			list = joinWithInDAO.getAllObjects(sort, page, size);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public JoinWithIn getOneByCondition(Map<String, Object> condition) {
		try {
				DB db = mongoDBClient.getReadMongoDB();
				joinWithInDAO.setPojo(new JoinWithIn());
				joinWithInDAO.getCollection("joinWithIn", db);

				return joinWithInDAO.findOneByCondition(condition);
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();

				throw new RAException(e.getMessage());
			}
		
	}
	public  List<JoinWithIn> getAllJoinWithIn(MongoSortVO sort){
		try {
			DB db = mongoDBClient.getReadMongoDB();
			joinWithInDAO.getCollection("joinWithIn", db);
			joinWithInDAO.setPojo(new JoinWithIn());
			List<JoinWithIn> list = joinWithInDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	

}
