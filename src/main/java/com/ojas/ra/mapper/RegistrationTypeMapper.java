package com.ojas.ra.mapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class RegistrationTypeMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("registrationType")
	private String registrationType;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

}
