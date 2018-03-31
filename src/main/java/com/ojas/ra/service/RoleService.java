package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.Role;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoSortVO;

public interface RoleService {

	boolean createRole(Role role) throws RAException;

	public Role findOneByCondition(Map<String, Object> condition);

	public List<Role> getByMapObjects(MongoSortVO sort, int page, int size, Map<String, Object> mapCondition)
			throws RAException;
}
