package com.ojas.ra.util;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ojas.ra.domain.Requirement;

@SuppressWarnings("serial")
public class Result implements Serializable {

	@JsonProperty
	private List<Requirement> list;

	public List<Requirement> getList() {
		return list;
	}

	public void setList(List<Requirement> list) {
		this.list = list;
	}


}
