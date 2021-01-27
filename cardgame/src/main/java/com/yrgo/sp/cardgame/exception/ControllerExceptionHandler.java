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

/**
 * @author elske 
 * 
 * https://www.baeldung.com/spring-boot-logging 
 * This class handles the exceptions for the CRUD Controller Classes.
 */
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * A logger to register the exception that has been casted
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	/**
	 * This method calls the logWarning method to log the details of
	 * cardnotfoundexception and returns a HTTP NotFound status.
	 * 
	 * @param ex
	 * @param request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(CardNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleCardNotFoundException(CardNotFoundException ex,
			WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * This method calls the logWarning method to log the details of
	 * categorynotfoundexception and returns a HTTP NotFound status.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CategoryNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleCategoryNotFoundException(CategoryNotFoundException ex,
			WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * This method calls the logWarning method to log the details of
	 * decknotfoundexception and returns a HTTP NotFound status.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DeckNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handDeckNotFoundException(DeckNotFoundException ex, WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * This method calls the logWarning method to log the details of
	 * playernotfoundexception and returns a HTTP NotFound status.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(PlayerNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handlePlayerNotFoundException(PlayerNotFoundException ex,
			WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * This method calls the logWarning method to log the details of global
	 * exceptions and returns a HTTP internal server error status.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {
		logWarning(ex, request.getDescription(false));
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * This method logs the details of the exceptions that are casted.
	 * The details consist of a timestamp, the exception that is casted and optional details from the webrequest.
	 * @param ex
	 * @param description
	 */
	private void logWarning(Exception ex, String description) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), description);
		LOG.warn(errorDetails.toString());
	}
}
