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
  public Flux<ResponseEntity<Teacher>> searchbyName(@PathVariable final String fullName) {
    return repository.searchbyName(fullName)
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.searchbyName >> fullName found: " + fullName))
    .map(show -> new ResponseEntity<>(show, HttpStatus.OK))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**
   * search by identification document number teacher document.
   * @param document identification document number
   * @return
   */
  
  @GetMapping("/documents/{document}")
  public Mono<ResponseEntity<Teacher>> searchbyDocument(@PathVariable final String document) {
    return repository.searchbyDocument(document)
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.searchbyDocument >> document found: " + document))
     .map(show -> new ResponseEntity<>(show, HttpStatus.OK))
     .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**
   * search by rank date of Birth teacher document.
   * @param fromDate date
   * @param toDate date
   * @return
   */
  
  @GetMapping("/dates/{fromDate}/{toDate}")
  public Flux<ResponseEntity<Teacher>> searchbyrankdateofBirth(
      @PathVariable @DateTimeFormat(iso = ISO.DATE) final Date fromDate,
      @PathVariable  @DateTimeFormat(iso = ISO.DATE)  final Date toDate) {
    return repository.searchbyrankdateofBirth(fromDate, toDate)
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.searchbyrankdateofBirth >> date of birth found: " 
    + fromDate + " between " + toDate))
    .map(show -> new ResponseEntity<>(show, HttpStatus.OK))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**
   * create record teacher document.
   * @param teacher teacher
   * @return
   */
  
  @PostMapping("/")
  public Mono<ResponseEntity<Teacher>> createTeacher(@Valid @RequestBody final Teacher teacher) {
    return repository.createTeacher(teacher)
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.createTeacher >> created"))
    .then(Mono.just(new ResponseEntity<Teacher>(HttpStatus.CREATED)))
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
  /**
   * show all record of teacher document.
   * @return
   */

  @GetMapping("/")
  public Flux<Teacher> allTeachers() {  
    return repository.allTeachers();
  }
  
  /**
   * modify record of teacher document.
   * @param id identification
   * @param teacher teacher
   * @return
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Teacher>> modifyTeacher(@PathVariable final String id,
      @Valid @RequestBody final Teacher teacher) {
    return repository.findbyId(id)
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.modifyTeacher >> id found"))
    .flatMap(people -> {
      people.setId(id);
      people.setFullName(teacher.getFullName());
      people.setGender(teacher.getGender());
      people.setDateofBirth(teacher.getDateofBirth());
      people.setTypeDocument(teacher.getTypeDocument());
      people.setDocumentNumber(teacher.getDocumentNumber());
      return repository.modifyTeacher(people);
    })
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.modifyTeacher >> modify"))
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
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.deleteteachers >> id found"))
    .flatMap(people ->
    repository.deleteTeacher(people)
    .doOnNext(mensaje -> 
    LOG.info("RestControllerTeacher.deleteteachers >> removed"))
    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))  
    )
    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
     
}
