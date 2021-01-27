package com.yrgo.sp.cardgame.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yrgo.sp.cardgame.domain.user.User;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@GetMapping(path = "/user")
	public ResponseEntity<User> getCurrentUser() {
		LOG.info("getCurrentUser");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User temp = new User();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			//user is not anonymous
			temp.setUserName(auth.getName());
			return new ResponseEntity<>(temp, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

	}
}
