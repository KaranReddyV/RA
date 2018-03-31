package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.ojas.ra.domain.Address;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

public interface AddressService {


	public List<Address> getAllObjects(MongoSortVO sort, int pageNo, int pageSize);

	public boolean removeByPrimaryId(String id);

	public Address findOneByCondition(Map<String, Object> condition);

	public List<Address> advancedFindByCondition(Map<String, MongoAdvancedQuery> addressMappingcondition,
			MongoSortVO sort, int pageNo, int pageSize);

	public Address findOneByPrimaryId(String value) throws RAException;

	public List<Address> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException;

	public boolean deleteAddress(ObjectId objId) throws RAException;

	public boolean saveAddress(Address address) throws RAException;

	public boolean createAddress(Address address) throws RAException;

	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target);


}
