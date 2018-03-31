package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;


@SuppressWarnings("serial")
public class JobType implements Serializable {
	
	@JsonProperty("_id")
  private ObjectId _id;

	@JsonProperty("jobType")
	private String jobType;
	
public ObjectId get_id() {
	return _id;
}
public void set_id(ObjectId _id) {
	this._id = _id;
}
public String getJobType() {
	return jobType;
}
public void setJobType(String jobType) {
	this.jobType = jobType;
}

}
