package com.codecool.poster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class PosterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosterApplication.class, args);
	}

}
