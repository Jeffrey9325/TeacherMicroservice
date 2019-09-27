package com.everis.controller;

import com.everis.model.Teacher;
import com.everis.repository.ReactiveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RestControllerTeacherTest {
	
	@Autowired
	  private ApplicationContext applicationContext;
	  @Autowired
	  private ReactiveRepository Repository;
	  
	  private WebTestClient client;
	  private List<Teacher> expectedProducts;


	  @BeforeEach
	  void setUp() {
	    client = WebTestClient
	      .bindToApplicationContext(applicationContext)
	      .configureClient()
	      .baseUrl("/Teacher/v1.0/")
	      .build();

	    Flux<Teacher> initData = Repository.deleteAll()
	      .thenMany(Flux.just(
	    		  Teacher.builder().id("14").fullName("pepito").gender("male").dateofBirth(LocalDate.of(1993, 2, 25)).typeDocument("dni").documentNumber("73674232").build(),
	    		  Teacher.builder().id("15").fullName("jeffrey").gender("male").dateofBirth(LocalDate.of(1997, 2, 25)).typeDocument("dni").documentNumber("47704995").build())
	        .flatMap(Repository::save))
	      .thenMany(Repository.findAll());

	    expectedProducts = initData.collectList().block();
	  }
	  
	  @Test
	  void searchbyName() {

	    String fullName = "pepito";
	    client.get().uri("/Teacher/v1.0/names/{fullName}", fullName).exchange()
	      .expectStatus().isOk();

	  }
	  
	  @Test
	  void searchbyDocument() {

	    String document = "47704995";
	    client.get().uri("/documents/{document}", document).exchange()
	      .expectStatus().isOk();

	  }
	  
	  @Test
	  void searchbyrankdateofBirth() {

	    LocalDate fromDate = LocalDate.of(1800,03,02);
	    LocalDate toDate = LocalDate.of(2000,03,02);


	      client.get().uri("/dates/{fromDate}/{toDate}", fromDate,toDate).exchange()
	        .expectStatus().isOk().
	    expectBodyList(Teacher.class);

	  }

	 @Test
	  void createTeacher() {
		 Teacher expectedProduct = expectedProducts.get(0);

	    client.post().uri("/").body(Mono.just(expectedProduct), Teacher.class).exchange()
	      .expectStatus().isCreated();


	  }

	  @Test
	  void allTeachers() {

	    client.get().uri("/").exchange()
	      .expectStatus().isOk();
	  }

	  

	  @Test
	  void modifyTeacher() {

	    Teacher expectedProduct = expectedProducts.get(0);

	    client.put().uri("/{id}", expectedProduct.getId()).body(Mono.just(expectedProduct), Teacher.class).exchange()
	      .expectStatus().isOk();
	  }

	  @Test
	  void deleteteachers() {

		  Teacher productToDelete = expectedProducts.get(0);
	    client.delete().uri("/{id}", productToDelete.getId()).exchange()
	      .expectStatus().isNoContent();
	  }  
}
