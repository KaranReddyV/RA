package com.ojas.ra.util;

import org.codehaus.jackson.map.ObjectMapper;



/**
 * @author sunil
 * 
 */
public class MongoMapper {

	/**
	 *  
	 */
	private static volatile ObjectMapper mapper = null;

	/**
	 *  
	 */
	private MongoMapper() {
	}

	/**
	 * Double-Checked Locking
	 * 
	 * @see http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
	 * 
	 * @return
	 */
	public static ObjectMapper getInstance() {

		if (null == mapper) {
			synchronized (ObjectMapper.class) {
				if (null == mapper) {
					mapper = new ObjectMapper();
				}
			}
		}

		return mapper;
	}
}