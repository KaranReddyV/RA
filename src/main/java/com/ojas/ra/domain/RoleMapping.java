package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class RoleMapping implements Serializable {

	/**
	 * 
	 */

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("user_id")
	private ObjectId user_id;

	@JsonProperty("role_id")
	private ObjectId role_id;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public ObjectId getUser_id() {
		return user_id;
	}

	public void setUser_id(ObjectId user_id) {
		this.user_id = user_id;
	}

	public ObjectId getRole_id() {
		return role_id;
	}

	public void setRole_id(ObjectId role_id) {
		this.role_id = role_id;
	}

}
