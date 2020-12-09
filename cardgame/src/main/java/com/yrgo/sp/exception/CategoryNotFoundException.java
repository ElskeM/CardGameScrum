package com.yrgo.sp.exception;

public class CategoryNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(String msg) {
		super(msg);
	}
}
