package com.ojas.ra.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoSortVO;
import com.ojas.ra.util.MongoUtil;

/**
 * @author sunil
 * 
 */
public class MongoDAO<T> implements IMongoDAO<T> {

	/**
	 * e
	 */
	private String collection;
	private T pojo;
	private DB db;
	private DBCollection coll;
	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 *  
	 */

	// ====================
	// public and protected
	// ====================

	/**
	 * : 1.:dsn,database,collection,pojo; 2.Spring.
	 * 
	 * @throws UnknownHostException
	 */

	public DBCollection getCollection(String collection, DB db) throws RAException {
		this.collection = collection;
		this.db = db;
		coll = this.db.getCollection(this.collection);
		if (null == this.db || null == this.collection) {
			throw new RAException("mongo: property 'db', 'coll' initializing failed!");
		}
		return coll;
	}

	/**
	 * Spring
	 */

	@Override
	public boolean insert(T object) throws RAException {

		boolean result = false;

		try {

			DBObject dbObject = MongoUtil.getDBObjectByObject(object);

			coll.insert(dbObject);
			result = getResult();
		} catch (Exception e) {
			throw new RAException("Error occur during the insert record" + e.getMessage());

		}

		return result;
	}

	@Override
	public boolean insertList(List<T> args) throws RAException {

		boolean result = false;

		try {

			List<DBObject> dbObjects = new ArrayList<DBObject>();

			if (null != args && 0 < args.size()) {
				for (T object : args) {
					dbObjects.add(MongoUtil.getDBObjectByObject(object));
				}
			}

			coll.insert(dbObjects);
			result = getResult();
		} catch (Exception e) {
			throw new RAException("Error occur during the insert record" + e.getMessage());

		}

		return result;
	}

	@Override
	public boolean save(T object) throws RAException {

		boolean result = false;

		try {

			DBObject dbObject = MongoUtil.getDBObjectByObject(object);

			coll.save(dbObject);
			result = getResult();
		} catch (Exception e) {
			throw new RAException("Error occur during the save record" + e.getMessage());

		}

		return result;
	}

	@Override
	public boolean updateMapByCondition(Map<String, Object> condition, Map<String, Object> target) throws RAException {

		boolean result = false;

		try {

			DBObject query = MongoUtil.getDBObjectByObject(condition);

			List<DBObject> dbObject = MongoUtil.getDBObjectByObjectList(target);
			for (DBObject obj : dbObject) {
				if (null != obj) {

					DBObject objNew = new BasicDBObject("$set", obj);

					coll.update(query, objNew, false, true);
				}
			}
			result = getResult();
		} catch (Exception e) {
			throw new RAException("Error occur during the save record" + e.getMessage());

		}

		return result;
	}

	@Override
	public boolean updateMapByKV(String field, String value, Map<String, Object> target) throws RAException {
		boolean b;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(field, value);

			b = updateMapByCondition(condition, target);
		} catch (Exception e) {
			throw new RAException("Error occur during the update record" + e.getMessage());

		}
		return b;

	}

	@Override
	public boolean updateMapByPrimaryId(String value, Map<String, Object> target) throws RAException {
		boolean b;
		try {
			b = updateMapByKV("_id", value, target);
		} catch (Exception e) {
			throw new RAException("Error occur during the update record" + e.getMessage());

		}

		return b;
	}

	@Override
	public boolean updateObjectByCondition(Map<String, Object> condition, T target) throws RAException {

		boolean result = false;

		try {

			DBObject query = MongoUtil.getDBObjectByObject(condition);

			DBObject dbObject = MongoUtil.getDBObjectByObject(target);
			dbObject.removeField("_id");

			DBObject objNew = new BasicDBObject("$set", dbObject);

			coll.update(query, objNew, false, true);
			result = getResult();
		} catch (Exception e) {
			throw new RAException("Error occur during the update record" + e.getMessage());

		}

		return result;
	}

	@Override
	public boolean updateObjectByKV(String field, String value, T target) throws RAException {
		boolean result = false;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(field, value);

			result = updateObjectByCondition(condition, target);
		} catch (Exception e) {
			throw new RAException("Error occur during the update record" + e.getMessage());

		}
		return result;
	}

	@Override
	public boolean updateObjectByPrimaryId(String value, T target) throws RAException {
		boolean result = false;
		try {
			result = updateObjectByKV("_id", value, target);
		} catch (Exception e) {
			throw new RAException("Error occur during the update record" + e.getMessage());

		}
		return result;
	}

	@Override
	public boolean incrByPrimaryId(String value, String column, int step) throws RAException {

		if (0 > step) {
			return false;
		}

		return incOperateByKV("_id", value, column, step);
	}

	@Override
	public boolean decrByPrimaryId(String value, String column, int step) throws RAException {

		if (0 < step) {
			return false;
		}

		return incOperateByKV("_id", value, column, step);
	}

	@Override
	public boolean removeByCondition(Map<String, Object> condition) throws RAException {

		boolean result = false;

		try {

			DBObject query = MongoUtil.getDBObjectByObject(condition);

			coll.remove(query);
			result = getResult();
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return result;
	}

	@Override
	public boolean removeByKV(String field, String value) throws RAException {
		boolean b;
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(field, value);

			b = removeByCondition(condition);
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}
		return b;
	}

	@Override
	public boolean removeByPrimaryId(String value) throws RAException {

		return removeByKV("_id", value);
	}

	@Deprecated
	@Override
	public boolean removeAll() throws RAException {

		boolean result = false;

		try {

			coll.remove(new BasicDBObject());
			result = getResult();
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOneByCondition(Map<String, Object> condition) throws RAException {

		T object = null;

		try {

			DBObject query = MongoUtil.getDBObjectByObject(condition);

			DBObject dbObject = coll.findOne(query);
			if (null != dbObject) {

				object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);

			}
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return object;
	}

	@Override
	public T findOneByKV(String field, String value) throws RAException {

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put(field, value);

		return findOneByCondition(condition);
	}

	@Override
	public T findOneByPrimaryId(String value) throws RAException {

		return findOneByKV("_id", value);
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	@Override
	public List<T> findAllByCondition(Map<String, Object> condition, MongoSortVO sort) throws RAException {

		List<T> resultList = new ArrayList<T>();

		try {

			DBObject query = MongoUtil.getDBObjectByObject(condition);

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find(query).sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {

					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}
			}
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return resultList;
	}

	@Override
	public List<T> findByCondition(Map<String, Object> condition, MongoSortVO sort, int page, int size)
			throws RAException {

		List<T> resultList = new ArrayList<T>();

		try {

			DBObject query = MongoUtil.getDBObjectByObject(condition);

			page = (0 < page ? page : 1);
			size = (0 < size ? size : 1);
			int skip = (page - 1) * size;

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find(query).skip(skip).limit(size).sort(order);
			MongoDAO<T> m1 = new MongoDAO<T>();
			m1.getCount(cursor, resultList);

		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return resultList;
	}

	@SuppressWarnings("unchecked")
	private int getCount(DBCursor cursor, List<T> resultList) throws Exception {
		if (null != cursor && 0 < cursor.count()) {
			for (DBObject dbObject : cursor) {
				T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
				if (null != object) {
					resultList.add(object);
				}
			}

		}
		return cursor.size();
	}

	@Override
	public long countByCondition(Map<String, Object> condition) throws RAException {

		long count = -1;

		try {
			DBObject query = MongoUtil.getDBObjectByObject(condition);

			count = coll.count(query);
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> advancedFindByCondition(Map<String, MongoAdvancedQuery> condition, MongoSortVO sort, int page,
			int size) throws RAException {

		List<T> resultList = new ArrayList<T>();

		if (null == condition) {
			return resultList;
		}

		try {

			DBObject query = MongoUtil.getDBObjectByAdvancedQuery(condition);

			page = (0 < page ? page : 1);
			size = (0 < size ? size : 1);
			int skip = (page - 1) * size;

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find(query).skip(skip).limit(size).sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}
			}
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}

		return resultList;
	}

	@Override
	public long advancedCountByCondition(Map<String, MongoAdvancedQuery> condition) throws RAException {

		long count = -1;

		if (null == condition) {
			return count;
		}

		try {

			DBObject query = MongoUtil.getDBObjectByAdvancedQuery(condition);

			count = coll.count(query);
		} catch (Exception e) {
			throw new RAException(e.getMessage());

		}

		return count;
	}

	// ====================
	// private methods
	// ====================

	/**
	 *
	 * 
	 * @return true|false
	 */
	private boolean getResult() {

		boolean result = true;

		return result;
	}

	/**
	 * $inc
	 * 
	 * @param field
	 * 
	 * @param value
	 * 
	 * @param column
	 * 
	 * 
	 * @return true|false
	 */
	private boolean incOperateByKV(String field, String value, String column, int step) throws RAException {

		boolean result = false;

		try {

			DBObject query = new BasicDBObject();
			query.put(field, value);

			DBObject dbObject = new BasicDBObject();
			dbObject.put(column, step);
			DBObject objNew = new BasicDBObject("$inc", dbObject);

			coll.update(query, objNew, false, true);
			result = getResult();
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}

		return result;
	}

	/**
	 *  
	 */

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setPojo(T pojo) {
		this.pojo = pojo;
	}

	@Override
	public void close() {

	}

	@Override
	public List<T> getAllObjects(MongoSortVO sort, int page, int size) throws RAException {

		List<T> resultList = new ArrayList<T>();
		try {

			page = (0 < page ? page : 1);
			size = (0 < size ? size : 1);
			int skip = (page - 1) * size;

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find().skip(skip).limit(size).sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					@SuppressWarnings("unchecked")
					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}
			}
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}

		return resultList;
	}

	@Override
	public List<T> getAllObjects(MongoSortVO sort) throws RAException {

		List<T> resultList = new ArrayList<T>();

		try {

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find().sort(order);
			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					@SuppressWarnings("unchecked")
					T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
					if (null != object) {
						resultList.add(object);
					}
				}

			}
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}

		return resultList;
	}

	@Override
	public int getCount(MongoSortVO sort) throws RAException {
		int size;
		try {

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find().sort(order);
			size = cursor.size();
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}

		return size;
	}

	@Override
	public int getPages(MongoSortVO sort,int pageSize) throws RAException {
		int pages;
		try {

			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);

			DBCursor cursor = coll.find().sort(order);
			int noOfRecords = cursor.size();
			pages = noOfRecords / pageSize;
			System.out.println(pages);
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}

		return pages;
	}

	public List<T> getByMapObjects(MongoSortVO sort, int pageNo, int pageSize, String mappedPojo,
			Map<String, Object> mapCondition) throws RAException {
		List<T> resultList = new ArrayList<T>();
		try {
			pageNo = (0 < pageNo ? pageNo : 1);
			pageSize = (0 < pageSize ? pageSize : 1);
			int skip = (pageNo - 1) * pageSize;
			DBObject order = MongoUtil.getDBObjectByMongoSort(sort);
			DBCursor cursor = coll.find().skip(skip).limit(pageSize).sort(order);

			if (null != cursor && 0 < cursor.count()) {
				for (DBObject dbObject : cursor) {
					DBCollection colls = db.getCollection(mappedPojo);
					colls.getCollection(mappedPojo);
					mapCondition.put("resourceId", dbObject.get("_id"));
					DBObject query = MongoUtil.getDBObjectByObject(mapCondition);
					DBCursor cursor1 = colls.find(query).skip(0).limit(1);
					if (null != cursor1 && cursor1.length() > 0) {
						@SuppressWarnings("unchecked")
						T object = (T) MongoUtil.getObjectByDBObject(dbObject, pojo);
						if (null != object) {
							resultList.add(object);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}
		return resultList;
	}

}