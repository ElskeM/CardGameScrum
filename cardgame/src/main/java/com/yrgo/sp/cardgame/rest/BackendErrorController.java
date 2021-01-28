package com.yrgo.sp.cardgame.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller hides body information about which specific error occurred on the backend server.
 * The HTTP headers (including status code) are preserved.
 * @author ptemrz
 *
 */
@RestController
public class BackendErrorController implements ErrorController {

	/** 
	 * @return String
	 */
	@RequestMapping("/error")
	public String handleError() {
		return "An error has occured";
	}

	/**
	 * @return String
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}
}
