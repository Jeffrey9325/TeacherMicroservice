package com.everis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@EnableSwagger2WebFlux
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TeacherMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacherMicroserviceApplication.class, args);
	}

}
