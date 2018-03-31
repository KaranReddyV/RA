package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.ojas.ra.domain.RoleMapping;
import com.ojas.ra.exception.RAException;

public interface RoleMappingService {

	boolean createRoleMapping(RoleMapping roleMapping) throws RAException;

	RoleMapping findOneByCondition(Map<String, Object> condition) throws RAException;

	List<RoleMapping> findOneRoleMappingByUserId(ObjectId id) throws RAException;

	public boolean removeByPrimaryId(String value) throws RAException;

	public boolean delete(ObjectId id) throws RAException;

	void removeByCondition(Map<String, Object> condition);


}
