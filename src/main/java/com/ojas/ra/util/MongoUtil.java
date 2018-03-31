package com.ojas.ra.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.json.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.ojas.ra.exception.RAException;

/**
 * @author sunil
 * 
 */
@SuppressWarnings("rawtypes")
public class MongoUtil {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static DBObject getDBObjectByAdvancedQuery(Map<String, MongoAdvancedQuery> map) throws Exception {

		DBObject result = null;

		if (null != map && 0 < map.size()) {
			Iterator it = map.entrySet().iterator();
			if (null != it) {
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					MongoAdvancedQuery value = (MongoAdvancedQuery) entry.getValue();
					if (null != value) {
						result = new BasicDBObject();
						result.put(key, value.getQueryObject());
					}
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @param sort
	 * @return
	 */
	public static DBObject getDBObjectByMongoSort(MongoSortVO sort) throws Exception {

		DBObject result = null;

		if (null != sort) {
			String primaryKey = sort.getPrimaryKey();
			MongoOrderByEnum primaryOrderBy = sort.getPrimaryOrderBy();
			if (null != primaryKey && 0 < primaryKey.length() && null != primaryOrderBy) {
				result = new BasicDBObject();
				result.put(primaryKey, primaryOrderBy.getValue());
			}
		}

		return result;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static DBObject getDBObjectByObject(Map<String, Object> map) throws Exception {

		DBObject result = null;

		if (null != map && 0 < map.size()) {
			Iterator it = map.entrySet().iterator();
			if (null != it) {
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					Object value = entry.getValue();
					result = new BasicDBObject();
					result.put(key, value);
				}
			}
		}

		return result;
	}

	public static List<DBObject> getDBObjectByObjectList(Map<String, Object> map) throws Exception {

		DBObject result = null;
		List<DBObject> list = new ArrayList<DBObject>(map.size());

		if (null != map && 0 < map.size()) {
			Iterator it = map.entrySet().iterator();
			if (null != it) {
				while (it.hasNext()) {
					Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					Object value = entry.getValue();
					result = new BasicDBObject();
					result.put(key, value);

					list.add(result);
				}
			}
		}

		return list;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static DBObject getDBObjectByObject(Object object) throws Exception {

		DBObject result = new BasicDBObject();
		Class beanClass = object.getClass();

		for (Field ff : beanClass.getDeclaredFields()) {
			ff.setAccessible(true);
			Object returnValue = null;

			String methodName = "get" + ff.getName().substring(0, 1).toUpperCase()
					+ ff.getName().substring(1, ff.getName().length());

			Method method = beanClass.getMethod(methodName, null);
			returnValue = method.invoke(object, null);
			if (null != returnValue) {
				result.put(ff.getName(), returnValue);
			}
		}
		try {

		} catch (Exception e) {
			throw new RAException(e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static Object getObjectByDBObject(DBObject source, Object target) throws Exception {

		Class beanClass = target.getClass();
		Object obj1 = target.getClass().newInstance();
		for (Field ff : beanClass.getDeclaredFields()) {
			ff.setAccessible(true);

			ff.set(obj1, source.get(ff.getName()));

		}

		return obj1;
	}

	public static Map getObjectByDBObject(Object target) throws RAException {
		Map<String, Object> objectAsMap = null;
		try {
			objectAsMap = new HashMap<String, Object>();
			BeanInfo info = Introspector.getBeanInfo(target.getClass());
			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				Method reader = pd.getReadMethod();
				if (reader != null)

					objectAsMap.put(pd.getName(), reader.invoke(target));
			}
		} catch (Exception e) {
			throw new RAException("Error occur during the converting Object to map");
		}
		objectAsMap.remove("class");
		objectAsMap.remove("_id");
		return objectAsMap;
	}

	/**
	 * 
	 * @param json
	 * @param beanClass
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@SuppressWarnings("unchecked")
	public static Object jsonToObject(String json, Class beanClass) throws Exception {
		Object object = null;

		ObjectMapper mapper = MongoMapper.getInstance();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		object = mapper.readValue(json, beanClass);

		return object;
	}

	/**
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static String objectToJson(Object object) throws Exception {

		ObjectMapper mapper = MongoMapper.getInstance();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper.writeValueAsString(object);
	}

}