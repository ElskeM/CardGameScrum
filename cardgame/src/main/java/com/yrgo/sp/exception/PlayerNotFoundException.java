package com.yrgo.sp.exception;

public class PlayerNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException(String msg) {
		super(msg);
	}
}
