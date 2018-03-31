package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.Payment;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoSortVO;

public interface PaymentService {

	public boolean createPayment(Payment payment) throws RAException;

	public boolean savePayment(Payment payment) throws RAException;

	public List<Payment> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	public Payment findOneByCondition(Map<String, Object> condition);

	public Payment findOneByPrimaryId(String value) throws RAException;

	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);

}
