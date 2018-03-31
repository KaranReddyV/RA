package com.ojas.ra.domain;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class AccessToken implements Serializable {
	/**
	 * 
	 */
	@JsonProperty("_id")
	private ObjectId _id;
	@JsonProperty("token")
	private String token;

	@JsonProperty("userName")

	private String userName;
	@JsonProperty("expiry")
	private Date expiry;

	public AccessToken() {
		/* Reflection instantiation */
	}

	public AccessToken(String userName, String token) {
		this.userName = userName;
		this.token = token;
		this.expiry = new Date();
	}

	public AccessToken(String userName, String token, Date expiry) {
		this(userName, token);
		this.expiry = expiry;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public String getToken() {
		return this.token;
	}

	public Date getExpiry() {
		return this.expiry;
	}

	public boolean isExpired() {
		if (null == this.expiry) {
			return false;
		}

		return this.expiry.getTime() > System.currentTimeMillis();
	}

}
