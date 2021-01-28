package com.yrgo.sp.cardgame.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author pontus
 * ServerHealthController entity to check on the servers status
 */
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class ServerHealthController {
	
	/**
	 * Method to check upon the status of the server
	 * @return ResponseEntity with a boolean
	 */
	@GetMapping("/status")
	public ResponseEntity<Boolean> getStatus() {	
		return new ResponseEntity<>(true, HttpStatus.OK);
		
	}


}
