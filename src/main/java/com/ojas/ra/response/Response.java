package com.ojas.ra.response;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Response {

	private String status;
	private HttpStatus errorCode;
	private String errorMessage;
	private ExceptionType exceptionType;
	private Object result;
	private int count;
	private int pages;

	public Response() {

	}

	/**
	 * Constructor
	 * 
	 * @param result
	 */

	public Response(String status) {
		this.status = status;
	}

	public Response(Object result) {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mapper.setDateFormat(df);

		try {

			this.result = mapper.writeValueAsString(result);
			JSONParser parser = new JSONParser();
			try {
				this.result = parser.parse((String) this.result);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public Response(Object result, String status) {
		this.status = status;
		this.result = result;
	}

	public Response(Object result, HttpStatus errorCode, String errorMessage) {
		this.result = result;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public Response(Object result, int pages, int count, HttpStatus errorCode, String errorMessage) {
		this.result = result;
		this.setPages(pages);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.count = count;
	}

	public Response(String status, HttpStatus errorCode, String errorMessage, ExceptionType exceptionType) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.exceptionType = exceptionType;
	}

	public Response(Object result, String status, HttpStatus errorCode, String errorMessage,
			ExceptionType exceptionType) {
		this.result = result;
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.exceptionType = exceptionType;
	}

	public Response(String status, HttpStatus errorCode, String errorMessage) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;

	}

	public Response(String status, HttpStatus errorCode) {
		this.status = status;
		this.errorCode = errorCode;
	}

	public Response(Object result, HttpStatus errorCode, String string, ExceptionType noException) {
		this.result = result;

	}

	/*
	 * * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the String
	 */
	public ExceptionType getString() {
		return exceptionType;
	}

	/**
	 * @param String
	 *            the String to set
	 */
	public void setString(ExceptionType String) {
		this.exceptionType = String;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	public int getcount() {
		return count;
	}

	public void setcount(int count) {
		this.count = count;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

}
