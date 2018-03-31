package com.ojas.ra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.ojas.ra.MongoDBClient;
import com.ojas.ra.dao.PaymentDAO;

import com.ojas.ra.domain.Payment;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.service.PaymentService;
import com.ojas.ra.util.MongoSortVO;
import com.ojas.ra.util.MongoUtil;

public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	MongoDBClient mongoDBClient;

	@Override
	public boolean createPayment(Payment payment) throws RAException {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);

			b = paymentDAO.insert(payment);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Override
	public boolean savePayment(Payment payment) throws RAException {

		boolean b;
		try {

			DB db = initializeDB();
			paymentDAO.getCollection("payment", db);
			paymentDAO.setPojo(new Payment());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", payment.get_id());
			b = paymentDAO.updateMapByCondition(condition, MongoUtil.getObjectByDBObject(payment));
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

	private DB initializeDB() {
		DB db = mongoDBClient.getReadMongoDB();
		paymentDAO.setPojo(new Payment());
		return db;

	}

	@Override
	public List<Payment> getAllObjects(MongoSortVO sort, int pageNo, int pageSize) throws RAException {
		List<Payment> list;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			paymentDAO.getCollection("payment", db);
			paymentDAO.setPojo(new Payment());
			list = paymentDAO.getAllObjects(sort, pageNo, pageSize);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return list;
	}

	@Override
	public Payment findOneByCondition(Map<String, Object> condition) throws RAException {

		try {
			DB db = mongoDBClient.getReadMongoDB();
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);

			return paymentDAO.findOneByCondition(condition);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}

	@Override
	public Payment findOneByPrimaryId(String value) throws RAException {
		try {
			DB db = mongoDBClient.getReadMongoDB();
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);

			return paymentDAO.findOneByPrimaryId(value);
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
	}
	
	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) {
		boolean b;
		try {
			DB db = mongoDBClient.getReadMongoDB();
			paymentDAO.setPojo(new Payment());
			paymentDAO.getCollection("payment", db);

			b= paymentDAO.updateMapByCondition(condition, target);
			mongoDBClient.closeMongoClient();
		} catch (RAException e) {
			mongoDBClient.closeMongoClient();

			throw new RAException(e.getMessage());
		}
		return b;
	}

}
