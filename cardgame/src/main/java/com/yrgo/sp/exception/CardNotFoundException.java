package com.yrgo.sp.exception;

public class CardNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CardNotFoundException(String msg) {
		super(msg);
	}

}
