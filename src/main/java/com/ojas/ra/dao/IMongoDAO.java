package com.ojas.ra.dao;

import java.util.List;
import java.util.Map;

import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;

/**
 * @author sunil
 * 
 */
public interface IMongoDAO<T> {

	/**
	 * 
	 * 
	 * @param object
	 *            pojo
	 * @return true|false
	 */
	public boolean insert(T object) throws RAException;

	/**
	 * ("_id")
	 * 
	 * @param objects
	 *            _id"
	 * @return true|false
	 */
	public boolean insertList(List<T> objects) throws RAException;

	/**
	 * 
	 * 
	 * @param object
	 *            pojo_id"
	 * @return true|false
	 */
	public boolean save(T object) throws RAException;;

	/**
	 * 
	 * UPDATE tblname SET xx=xx... WHERE key1=value1 AND key2=value2...
	 * 
	 * 
	 * @param condition
	 *            key:value:
	 * @param target
	 * @return true|false
	 */
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) throws RAException;;

	/**
	 * 
	 * UPDATE tblname SET xx=xx... WHERE field=value
	 * 
	 * 
	 * @param field
	 * @param value
	 * @param target
	 * @return true|false
	 */
	public boolean updateMapByKV(String field, String value, Map<String, Object> target) throws RAException;;

	/**
	 * "_id""_id value UPDATE tblname SET xx=xx... WHERE _id=value
	 * 
	 * 
	 * @param value
	 *            "_id
	 * @param target
	 * @return true|false
	 */
	public boolean updateMapByPrimaryId(String value, Map<String, Object> target) throws RAException;;

	/**
	 *
	 * UPDATE tblname SET xx=xx... WHERE key1=value1 AND key2=value2...
	 * 
	 * 
	 * @param condition
	 *            key:,value:
	 * @param object
	 *            pojo
	 * @return false
	 */
	public boolean updateObjectByCondition(Map<String, Object> condition, T target) throws RAException;;

	/**
	 * 
	 * UPDATE tblname SET xx=xx... WHERE field=value
	 * 
	 * 
	 * @param field
	 * @param value
	 * @param object
	 * @return true|false
	 */
	public boolean updateObjectByKV(String field, String value, T target) throws RAException;

	/**
	 * _id" "value" UPDATE tblname SET xx=xx... WHERE _id=value
	 * 
	 * 
	 * @param value
	 *            "_id"
	 * @param object
	 *            pojo
	 * @return true|false
	 */
	public boolean updateObjectByPrimaryId(String value, T target) throws RAException;

	/**
	 * "_id" :UPDATE tblname field=field+step SET WHERE _id=value
	 * 
	 * @param value
	 *            "_id"
	 * @param column
	 * @param step
	 * @return true|false
	 */
	public boolean incrByPrimaryId(String value, String column, int step) throws RAException;

	/**
	 * "_id" :UPDATE tblname field=field-step SET WHERE _id=value
	 * 
	 * @param value
	 *            "_id"
	 * @param column
	 * @param step
	 * @return true|false
	 */
	public boolean decrByPrimaryId(String value, String column, int step) throws RAException;

	/**
	 * 
	 * DELETE FROM tblname WHERE key1=value1 AND key2=value2...
	 * 
	 * @param condition
	 *            key:,value:
	 * @return true|false
	 */
	public boolean removeByCondition(Map<String, Object> condition) throws RAException;

	/**
	 * 
	 * DELETE FROM tblname WHERE field=value
	 * 
	 * @param field
	 * @param value
	 * @return true|false
	 */
	boolean removeByKV(String field, String value) throws RAException;

	/**
	 * "_id""_id" "value" DELETE FROM tblname WHERE _id=value
	 * 
	 * @param value
	 *            "_id"
	 * @return true|false
	 */
	public boolean removeByPrimaryId(String value) throws RAException;

	/**
	 * 
	 * 
	 * @return
	 */
	@Deprecated
	public boolean removeAll() throws RAException;

	/**
	 * 
	 * SELECT * FROM tblname WHERE key1=value1 AND key2=value2... LIMIT 1
	 * 
	 * @param condition
	 *            key:,value:
	 * @return
	 */
	public T findOneByCondition(Map<String, Object> condition) throws RAException;

	/**
	 * 
	 * :SELECT * FROM tblname WHERE field=value LIMIT 1
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public T findOneByKV(String field, String value) throws RAException;

	/**
	 * "_id" "_id""value" SELECT * FROM tblname WHERE _id=value LIMIT 1
	 * 
	 * @param value
	 *            "_id"
	 * @return
	 */
	public T findOneByPrimaryId(String value) throws RAException;

	/**
	 * 
	 * SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key
	 * DESC|ASC
	 * 
	 * @param condition
	 *            key:,value:
	 * @param sort
	 * @return
	 */
	@Deprecated
	public List<T> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException;

	/**
	 * 
	 * :SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key
	 * DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<T> findByCondition(Map<String, Object> condition, MongoSortVO sort, int page, int size)
			throws RAException;

	/**
	 * 
	 * SELECT COUNT(*) FROM tblname WHERE key1=value1 AND key2=value2... ORDER
	 * BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:
	 * @return
	 */
	public long countByCondition(Map<String, Object> condition) throws RAException;

	/**
	 * 
	 * :SELECT * FROM tblname WHERE key1=value1 AND key2=value2... ORDER BY key
	 * DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:AdvancedQuery
	 * @param sort
	 * @param page
	 * @param size
	 * @return
	 */
	public List<T> advancedFindByCondition(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page,
			int size) throws RAException;

	/**
	 *
	 * 类似:SELECT COUNT(*) FROM tblname WHERE key1=value1 AND key2=value2...
	 * ORDER BY key DESC|ASC LIMIT x,y
	 * 
	 * @param condition
	 *            key:,value:AdvancedQuery
	 * @return
	 */
	public long advancedCountByCondition(Map<String, MongoAdvancedQuery> condition) throws RAException;

	/**
	 * closes the underlying connector, which in turn closes all open
	 * connections. Once called, this Mongo instance can no longer be used.
	 */
	public void close();

	public List<T> getAllObjects(MongoSortVO sort, int page, int size) throws RAException;

	public List<T> getAllObjects(MongoSortVO sort) throws RAException;

	int getCount(MongoSortVO sort) throws RAException;

	public List<T> getByMapObjects(MongoSortVO sort, int page, int size, String mappedPojo,
			Map<String, Object> mapCondition) throws RAException;

	int getPages(MongoSortVO sort, int pageSize) throws RAException;

}