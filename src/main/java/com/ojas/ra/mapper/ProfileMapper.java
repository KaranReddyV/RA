package com.ojas.ra.mapper;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class ProfileMapper implements Serializable {

	@JsonProperty("_id")
	private String _id;

	@JsonProperty("nameOfTheCompany")
	private String nameOfTheCompany;

	@JsonProperty("regdNo")
	private String regdNo;

	@JsonProperty("yearOfESTD")
	private String yearOfESTD;

	@JsonProperty("companyType")
	private String companyType;

	@JsonProperty("qualityCertificates")
	private String qualityCertificates;

	@JsonProperty("other")
	private String other;

	@JsonProperty("emailId")
	private String emailId;

	@JsonProperty("contactNo")
	private String contactNo;

	@JsonProperty("url")
	private String url;

	@JsonProperty("noOfSkilled")
	private String noOfSkilled;

	@JsonProperty("noOfUnSkilled")
	private String noOfUnSkilled;

	@JsonProperty("noOfOtherSupport")
	private String noOfOtherSupport;

	@JsonProperty("streetNo")
	private String streetNo;

	@JsonProperty("zipcode")
	private String zipcode;

	@JsonProperty("mandal")
	private String mandal;

	@JsonProperty("city")
	private String city;

	@JsonProperty("state")
	private String state;

	@JsonProperty("country")
	private String country;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getNameOfTheCompany() {
		return nameOfTheCompany;
	}

	public void setNameOfTheCompany(String nameOfTheCompany) {
		this.nameOfTheCompany = nameOfTheCompany;
	}

	public String getRegdNo() {
		return regdNo;
	}

	public void setRegdNo(String regdNo) {
		this.regdNo = regdNo;
	}

	public String getYearOfESTD() {
		return yearOfESTD;
	}

	public void setYearOfESTD(String yearOfESTD) {
		this.yearOfESTD = yearOfESTD;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getQualityCertificates() {
		return qualityCertificates;
	}

	public void setQualityCertificates(String qualityCertificates) {
		this.qualityCertificates = qualityCertificates;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNoOfSkilled() {
		return noOfSkilled;
	}

	public void setNoOfSkilled(String noOfSkilled) {
		this.noOfSkilled = noOfSkilled;
	}

	public String getNoOfUnSkilled() {
		return noOfUnSkilled;
	}

	public void setNoOfUnSkilled(String noOfUnSkilled) {
		this.noOfUnSkilled = noOfUnSkilled;
	}

	public String getNoOfOtherSupport() {
		return noOfOtherSupport;
	}

	public void setNoOfOtherSupport(String noOfOtherSupport) {
		this.noOfOtherSupport = noOfOtherSupport;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMandal() {
		return mandal;
	}

	public void setMandal(String mandal) {
		this.mandal = mandal;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
