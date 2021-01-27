package com.yrgo.sp.cardgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ptemrz
 * Entity for passwordconfiguration 
 */
@Configuration
public class PasswordConfiguration {

	/**
	 * Method to encrypt passwords
	 * @return encrypted password
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
