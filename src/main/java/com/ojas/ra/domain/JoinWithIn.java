package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;

@SuppressWarnings("serial")
public class JoinWithIn implements Serializable {
	private ObjectId _id;
	private String joinWithIn;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getJoinWithIn() {
		return joinWithIn;
	}

	public void setJoinWithIn(String joinWithIn) {
		this.joinWithIn = joinWithIn;
	}

}
