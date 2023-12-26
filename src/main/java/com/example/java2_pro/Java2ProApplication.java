package com.example.java2_pro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Java2ProApplication {
	private static final Logger LOGGER= LoggerFactory.getLogger(Java2ProApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(Java2ProApplication.class, args);
	}

}
