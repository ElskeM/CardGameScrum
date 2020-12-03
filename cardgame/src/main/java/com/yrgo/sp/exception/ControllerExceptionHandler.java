package com.yrgo.sp.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

	
	@ExceptionHandler(CardNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage cardNotFoundException(CardNotFoundException exception, WebRequest request) {
		ErrorMessage msg = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(), 
				new Date(),
				exception.getMessage(),
				request.getDescription(false));
		return msg;
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage categoryNotFoundException(CategoryNotFoundException exception, WebRequest request) {
		ErrorMessage msg = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(), 
				new Date(),
				exception.getMessage(),
				request.getDescription(false));
		return msg;		
	}
	
	@ExceptionHandler(DeckNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage deckNotFoundException(DeckNotFoundException exception, WebRequest request) {
		ErrorMessage msg = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(), 
				new Date(),
				exception.getMessage(),
				request.getDescription(false));
		return msg;
	}
	
	@ExceptionHandler(PlayerNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage playerNotFoundException(PlayerNotFoundException exception, WebRequest request) {
		ErrorMessage msg = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(), 
				new Date(),
				exception.getMessage(),
				request.getDescription(false));
		return msg;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception e, WebRequest request) {
		ErrorMessage msg = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				new Date(),
				e.getMessage(),
				request.getDescription(false));
		return msg;
	}
}
