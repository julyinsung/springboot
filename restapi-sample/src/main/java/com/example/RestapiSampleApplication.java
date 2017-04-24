package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@ComponentScan
@EnableWebMvc
public class RestapiSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiSampleApplication.class, args);
	}
}
