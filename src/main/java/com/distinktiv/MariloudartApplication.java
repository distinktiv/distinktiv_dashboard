package com.distinktiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class MariloudartApplication {

	public static void main(String[] args) {
		SpringApplication.run(MariloudartApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer(){
		return container ->{
			ErrorPage errorPage401 = new ErrorPage(HttpStatus.UNAUTHORIZED,"/401.html");
			ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/404.html");
			ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500.html");

			container.addErrorPages(errorPage401, errorPage404, errorPage500);
		};
	}
}
