package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class Role implements Serializable {

	/**
	 * 
	 */

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("roleName")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Role() {
		super();
	}

}
