package com.yrgo.sp.exception;

import java.util.Date;

public class ErrorMessage {
	
	private int statusCode;
	private Date timeStamp;
	private String errorMessage;
	private String descr;
	
	public ErrorMessage(int statusCode, Date timeStamp, String errorMessage, String descr) {
		this.statusCode = statusCode;
		this.timeStamp = timeStamp;
		this.errorMessage = errorMessage;
		this.descr = descr;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getDescr() {
		return descr;
	}

}
