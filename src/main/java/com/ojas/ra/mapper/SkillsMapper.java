package com.ojas.ra.mapper;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class SkillsMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("name")
	private String name;

	public SkillsMapper() {
		super();
	}

	public SkillsMapper(String _id, String name) {
		super();
		this._id = _id;
		this.name = name;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
