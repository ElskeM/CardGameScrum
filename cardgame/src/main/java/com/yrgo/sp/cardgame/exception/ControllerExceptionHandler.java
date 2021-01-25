package com.yrgo.sp.cardgame.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(CardNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleCardNotFoundException(CardNotFoundException ex,
			WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleCategoryNotFoundException(CategoryNotFoundException ex,
			WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DeckNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handDeckNotFoundException(DeckNotFoundException ex, WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PlayerNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handlePlayerNotFoundException(PlayerNotFoundException ex,
			WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private void logWarning(Exception ex, String description) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), description);
		LOG.warn(errorDetails.toString());	
	}
}