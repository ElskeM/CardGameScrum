package com.yrgo.sp.cardgame;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class CardgameApplication {

	public static final Logger logger = LoggerFactory.getLogger(CardgameApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CardgameApplication.class, args);
	}

}
