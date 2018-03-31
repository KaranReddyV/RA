package com.ojas.ra.exception;

public class RAException extends RuntimeException {

	/**
	 *  
	 */
	private static final long serialVersionUID = 5646510831551284097L;

	/**
	 *  
	 */
	public RAException() {
		super();
	}

	/**
	 * 
	 * @param message
	 */
	public RAException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public RAException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param cause
	 */
	public RAException(Throwable cause) {
		super(cause);
	}
}