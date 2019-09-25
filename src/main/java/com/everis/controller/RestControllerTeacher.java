package com.everis.controller;


import com.everis.model.Teacher;
import com.everis.service.TeacherServiceImpl;

import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Teacher/v1.0")
public class RestControllerTeacher {
	
	private static Logger LOG = LoggerFactory.getLogger(RestControllerTeacher.class);
  /**
   * Teacher Service Implement.
   */
  @Autowired
  public TeacherServiceImpl repository;
  /**
   * search by full name teacher document.
   * @param fullName full name
   * @return
   */
  
  @GetMapping("/names/{fullName}")
  public Flux<Teacher> searchbyName(@PathVariable final String fullName) {
    return repository.searchbyName(fullName);
  }
  /**
   * search by identification document number teacher document.
   * @param document identification document number
   * @return
   */
  
  @GetMapping("/documents/{document}")
  public Mono<Teacher> searchbyDocument(@PathVariable final String document) {
    return repository.searchbyDocument(document);
  }
  /**
   * search by rank date of Birth teacher document.
   * @param fromDate date
   * @param toDate date
   * @return
   */
  
  @GetMapping("/dates/{fromDate}/{toDate}")
  public Flux<Teacher> searchbyrankdateofBirth(
      @PathVariable @DateTimeFormat(iso = ISO.DATE) final Date fromDate,
      @PathVariable  @DateTimeFormat(iso = ISO.DATE)  final Date toDate) {
    return repository.searchbyrankdateofBirth(fromDate, toDate);
  }
  /**
   * create record teacher document.
   * @param Teacher teacher
   * @return
   */
  
  @PostMapping("/")
  public Mono<ResponseEntity<Teacher>> createTeacher(@Valid @RequestBody final Teacher teacher) {
    return repository.createTeacher(teacher)
    .then(Mono.just(new ResponseEntity<Teacher>(HttpStatus.CREATED)))
   
//    .switchIfEmpty(Mono.error(new ProductNotFoundException("vacio")))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**
   * show all record of teacher document.
   * @return
   */
  
  //@HystrixCommand(fallbackMethod = "metodoalternativo")
  @GetMapping("/")
  public Flux<Teacher> allTeachers() {  
    return repository.allTeachers();
  }
  
//  
//	  String <teachers> teacher = new teachers("17", "pepito", "m", "dni", date, "14528985"); 
//	  teacher.flatMap(mapper)
//	  List<String> teacher =  Arrays.asList("17", "pepito", "m", "dni", date.toString(), "14528985"); 
//	  teacher.stream()
//	  

	
	  
//	  teacher.setId("17");
//	  teacher.setFullName("pepito");
//	  teacher.setGender("m");
//	  teacher.setTypeDocument("dni");
//	  teacher.setDateofBirth(date);
//	  teacher.setDocumentNumber("14528985");
  /**
   * modify record of teacher document.
   * @param id identification
   * @param Teacher teacher
   * @return
   */
  
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Teacher>> modifyTeacher(@PathVariable final String id,
      @Valid @RequestBody final Teacher teacher) {
    return repository.findbyId(id)
    .flatMap(people -> {
      people.setId(id);
      people.setFullName(teacher.getFullName());
      people.setGender(teacher.getGender());
      people.setDateofBirth(teacher.getDateofBirth());
      people.setTypeDocument(teacher.getTypeDocument());
      people.setDocumentNumber(teacher.getDocumentNumber());
      return repository.modifyTeacher(people);
    })
    .map(update -> new ResponseEntity<>(update, HttpStatus.OK))
    //.orElseThrow(() -> new ProductNotFoundException("vacio"))
    //.orElseThrow(Mono.error(new ProductNotFoundException("vacio")))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**
   * delete record in teacher document.
   * @param id identification
   * @return
   */
 
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteteachers(@PathVariable final String id) {
    return repository.findbyId(id)
    .flatMap(people ->
    repository.deleteTeacher(people)
    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))  
    )
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
   
}
