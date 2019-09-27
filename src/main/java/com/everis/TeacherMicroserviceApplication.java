package com.everis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@EnableEurekaClient
@EnableSwagger2WebFlux
@SpringBootApplication
public class TeacherMicroserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(TeacherMicroserviceApplication.class, args);
  }

}
