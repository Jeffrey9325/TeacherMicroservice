package com.everis.service;

import com.everis.model.Teacher;

import java.util.Date;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * ITeacherService interface.
 * @author jeffrey
 * @version v1.0
 */

public interface ITeacherService {
  /**
   * search by name teacher document.
   * @param name full name teacher
   * @return
   */

  Flux<Teacher> searchbyName(String name);
  /**
   * search by document teacher document.
   * @param document identification document number
   * @return
   */
  
  Mono<Teacher> searchbyDocument(String document);
  /**
   * search by rank date of birth.
   * @param fromDate date
   * @param toDate date
   * @return
   */
  
  Flux<Teacher> searchbyrankdateofBirth(Date fromDate, Date toDate);
  /**
   * create record teacher document.
   * @param teacher teacher
   * @return
   */
  
  Mono<Teacher> createTeacher(Teacher teacher);
  /**
   * show all teacher of teacher document.
   * @return
   */
  
  Flux<Teacher> allTeachers();
  /**
   * modify record teacher document.
   * @param teacher teacher
   * @return
   */
  
  Mono<Teacher> modifyTeacher(Teacher teacher);
  /**
   * delete record of teacher document.
   * @param teacher teacher
   * @return
   */
  
  Mono<Void> deleteTeacher(Teacher teacher);
  /**
   * find by id teacher document.
   * @param idTeacher id
   * @return
   */
  
  Mono<Teacher> findbyId(String idTeacher);
  
}