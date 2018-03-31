package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.RegistrationType;
import com.ojas.ra.util.MongoSortVO;

public interface RegistrationTypeService {
	public boolean createRegistrationType(RegistrationType registrationType);

	public boolean updateRegistrationType(RegistrationType registrationType);

	public List<RegistrationType> getAllRegistrationType(MongoSortVO sort, int page, int size);

	public List<RegistrationType> getAllRegistrationType(MongoSortVO sort);

	public RegistrationType getOneByCondition(Map<String, Object> condition);
	

}
