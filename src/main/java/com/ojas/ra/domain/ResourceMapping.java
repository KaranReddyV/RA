package com.ojas.ra.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class ResourceMapping implements Serializable {
	/*
	 * 
	 * varialble declaration
	 */

	@JsonProperty("_id")
	private ObjectId _id;

	@JsonProperty("companyId")
	private ObjectId companyId;

	@JsonProperty("vendorId")
	private ObjectId vendorId;

	@JsonProperty("resourceId")
	private ObjectId resourceId;

	@JsonProperty("requirementId")
	private ObjectId requirementId;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public ObjectId getCompanyId() {
		return companyId;
	}

	public void setCompanyId(ObjectId companyId) {
		this.companyId = companyId;
	}

	public ObjectId getVendorId() {
		return vendorId;
	}

	public void setVendorId(ObjectId vendorId) {
		this.vendorId = vendorId;
	}

	public ObjectId getResourceId() {
		return resourceId;
	}

	public void setResourceId(ObjectId resourceId) {
		this.resourceId = resourceId;
	}

	public ObjectId getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(ObjectId requirementId) {
		this.requirementId = requirementId;
	}

}
