package com.rapidquotation.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.rapidquotation.themeleafconfig","com.rapidquotation.security","com.rapidquotation.pdfService","com.rapidquotation.controllers" ,"com.rapidquotation.service" , "com.rapidquotation.serviceImpl"} )
@EntityScan(basePackages = {"com.rapidquotation.entities"})
@EnableJpaRepositories(basePackages = {"com.rapidquotation.repository"})
public class RapidQuotationApplication {

	public static void main(String[] args) {
		 SpringApplication.run(RapidQuotationApplication.class, args);
		
		System.out.println("hello world..");
		System.out.println("hi");
	}

}
