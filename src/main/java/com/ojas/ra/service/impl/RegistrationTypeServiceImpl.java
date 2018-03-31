package com.ojas.ra.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.RegistrationTypeDAO;
import com.ojas.ra.domain.RegistrationType;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.RegistrationTypeService;
import com.ojas.ra.util.MongoSortVO;

public class RegistrationTypeServiceImpl implements RegistrationTypeService {
	@Autowired
	RegistrationTypeDAO registrationTypeDAO;
	@Autowired
	MongoDBClient mongoDBClient;

	@Override
	public boolean createRegistrationType(RegistrationType registrationType) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);

			b = registrationTypeDAO.insert(registrationType);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean updateRegistrationType(RegistrationType registrationType) {
		boolean b;
		try {

			DB db = mongoDBClient.getReadMongoDB();
			registrationTypeDAO.setPojo(new RegistrationType());
			registrationTypeDAO.getCollection("registrationType", db);

			b = registrationTypeDAO.save(registrationType);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public List<RegistrationType> getAllRegistrationType(MongoSortVO sort, int page, int size) {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationTypeDAO.getCollection("registrationType", db);
			registrationTypeDAO.setPojo(new RegistrationType());
			List<RegistrationType> list = registrationTypeDAO.getAllObjects(sort, page, size);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}

	}
	public  List<RegistrationType> getAllRegistrationType(MongoSortVO sort){
		try {
			DB db = mongoDBClient.getReadMongoDB();
			registrationTypeDAO.getCollection("registrationType", db);
			registrationTypeDAO.setPojo(new RegistrationType());
			List<RegistrationType> list = registrationTypeDAO.getAllObjects(sort);
			return list;
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public RegistrationType getOneByCondition(Map<String, Object> condition) {
		
			try {
				DB db = mongoDBClient.getReadMongoDB();
				registrationTypeDAO.setPojo(new RegistrationType());
				registrationTypeDAO.getCollection("registrationType", db);

				return registrationTypeDAO.findOneByCondition(condition);
			} catch (RAException e) {
				mongoDBClient.closeMongoClient();

				throw new RAException(e.getMessage());
			}
		
	}
	}

