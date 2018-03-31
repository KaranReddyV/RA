package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class JobCategory implements Serializable {
	@JsonProperty("_id")
	private ObjectId _id;

	private String jobCategory;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

}
