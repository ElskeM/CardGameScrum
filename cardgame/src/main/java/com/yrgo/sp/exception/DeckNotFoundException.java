package com.yrgo.sp.exception;

public class DeckNotFoundException extends IllegalArgumentException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeckNotFoundException(String msg) {
		super(msg);
	}
}
