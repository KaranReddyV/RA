package com.ojas.ra.mapper;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author skkhadar
 *
 */
@SuppressWarnings("serial")
public class RequirementMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("jobCategory")
	private String jobCategory;

	@JsonProperty("jobType")
	private String jobType;

	@JsonProperty("jobTitle")
	private String jobTitle;

	@JsonProperty("description")
	private String description;

	@JsonProperty("jobLocation")
	private String jobLocation;

	@JsonProperty("rateType")
	private String rateType;

	@JsonProperty("experience")
	private String experience;

	@JsonProperty("primarySkills")
	private String primarySkills;

	@JsonProperty("secondarySkills")
	private String secondarySkills;

	@JsonProperty("resources")
	private String resources;

	@JsonProperty("joinWithin")
	private String joinWithin;

	@JsonProperty("registrationId")
	private String registrationId;

	@JsonProperty("status")
	private String status;

	@JsonProperty("uploadFile")
	private String uploadFile;

	public String getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public RequirementMapper() {
		super();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPrimarySkills() {
		return primarySkills;
	}

	public void setPrimarySkills(String primarySkills) {
		this.primarySkills = primarySkills;
	}

	public String getSecondarySkills() {
		return secondarySkills;
	}

	public void setSecondarySkills(String secondarySkills) {
		this.secondarySkills = secondarySkills;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getJoinWithin() {
		return joinWithin;
	}

	public void setJoinWithin(String joinWithin) {
		this.joinWithin = joinWithin;
	}

}
