package com.app.datapart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}



}
