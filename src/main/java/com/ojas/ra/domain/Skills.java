package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class Skills implements Serializable {

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("name")
	private String name;

	public Skills() {
		super();
	}

	public Skills(ObjectId _id, String name) {
		super();
		this._id = _id;
		this.name = name;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
