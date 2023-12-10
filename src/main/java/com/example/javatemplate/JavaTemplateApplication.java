package com.example.javatemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.javatemplate"})
public class JavaTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTemplateApplication.class, args);
	}

}
