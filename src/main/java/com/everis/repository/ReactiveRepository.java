package com.everis.repository;

import com.everis.model.Teacher;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * ReactiveRepository interface.
 * @author jeffrey
 * @version v1.0
 */

@Repository
public interface ReactiveRepository extends ReactiveMongoRepository<Teacher, String> {
  /**
   * find by Full Name teacher document.
   * @param fullName full name
   * @return
   */

  Flux<Teacher> findByFullName(String fullName);
  /**
   * find by identification document number teacher document.
   * @param documentNumber identification document number
   * @return
   */
  
  Mono<Teacher> findByDocumentNumber(String documentNumber);
  /**
   * find by rank date of birth teacher document.
   * @param fromDate date
   * @param toDate date
   * @return
   */
  
  Flux<Teacher> findByDateofBirthBetween(Date fromDate, Date toDate);
  /**
   * find by id teacher document.
   * @param idTeacher id
   * @return
   */
  
  Mono<Teacher> findById(String idTeacher);
  
  
  
}
