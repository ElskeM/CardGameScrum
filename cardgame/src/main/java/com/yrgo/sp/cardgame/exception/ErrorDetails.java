package com.yrgo.sp.cardgame.exception;

import java.util.Date;

/**
 * @author elske
 * This class created the details of a casted exception.
 * The errordetails consist of a timestamp, a message and webrequest details.
 */
public class ErrorDetails {

	private Date timestamp;
	private String message;
	private String details;

	/**
	 * Constructor for the ErrorDetails class. 
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	/**
	 * ToString method. The details are returned and logged in the ControllerExceptionHandler class.
	 */
	@Override
	public String toString() {
		return "ErrorDetails [timestamp=" + timestamp + ", message=" + message + ", details=" + details + "]";
	}
	
}
