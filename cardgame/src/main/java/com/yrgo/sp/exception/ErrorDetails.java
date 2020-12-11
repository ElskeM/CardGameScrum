package com.yrgo.sp.exception;

import java.util.Date;

public class ErrorDetails {

	private int statusCode;
	private Date timeStamp;
	private String errorMessage;
	private String descr;

	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}
