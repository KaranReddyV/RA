package com.ojas.ra.mapper;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class JoinWithInMapper implements Serializable {
	@JsonProperty("_id")
	private String _id;
	
	@JsonProperty("joinWithIn")
	private String joinWithIn;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getJoinWithIn() {
		return joinWithIn;
	}

	public void setJoinWithIn(String joinWithIn) {
		this.joinWithIn = joinWithIn;
	}
}
