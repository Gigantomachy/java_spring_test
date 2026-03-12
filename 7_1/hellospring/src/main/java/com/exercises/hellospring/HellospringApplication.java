package com.exercises.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HellospringApplication {

	// Spring Boot creates and configures the DispatcherServlet automatically for us
	public static void main(String[] args) {
		SpringApplication.run(HellospringApplication.class, args);
	}

}
