package com.ojas.ra.service;

import java.util.List;
import java.util.Map;

import com.ojas.ra.domain.JoinWithIn;
import com.ojas.ra.util.MongoSortVO;

public interface JoinWithInService {

	public boolean createJoinWithIn(JoinWithIn joinWithIn);

	public boolean updateJoinWithIn(JoinWithIn joinWithIn);

	public List<JoinWithIn> getAllJoinWithIn(MongoSortVO sort, int page, int size);

	public JoinWithIn getOneByCondition(Map<String, Object> condition);

	public List<JoinWithIn> getAllJoinWithIn(MongoSortVO sort);

}
