package com.ojas.ra;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.ra.exception.RAException;

public class MongoServiceFactory implements FactoryBean<MongoDBClient> {

	@Autowired
	MongoConfig mongoConfig;

	@Override
	public MongoDBClient getObject() throws RAException {
		// TODO Auto-generated method stub
		return new MongoDBClientImpl(mongoConfig);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return MongoDBClientImpl.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

}
