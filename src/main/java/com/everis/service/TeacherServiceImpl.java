package com.everis.service;

import com.everis.model.Teacher;
import com.everis.repository.ReactiveRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TeacherServiceImpl class.
 * @author jeffrey
 * @version v1.0
 */

@Service
public class TeacherServiceImpl implements ITeacherService {
  /**
   * Reactive Repository.
   */
  @Autowired
  private ReactiveRepository repository;

  @Override
  public Flux<Teacher> searchbyName(String name) {
    return repository.findByFullName(name);
  }

  @Override
  public Mono<Teacher> searchbyDocument(String document) {
    return repository.findByDocumentNumber(document);
  }

  @Override
  public Flux<Teacher> searchbyrankdateofBirth(Date fromDate, Date toDate) {
    return repository.findByDateofBirthBetween(fromDate, toDate);
  }

  @Override
  public Mono<Teacher> createTeacher(Teacher teacher) {
    return repository.save(teacher);
  }

  @Override
  public Flux<Teacher> allTeachers() {
    return repository.findAll();
  }

  @Override
  public Mono<Teacher> modifyTeacher(Teacher teacher) {
    return repository.save(teacher);
  }

  @Override
  public Mono<Void> deleteTeacher(Teacher teacher) {
    return repository.delete(teacher);
  }

  @Override
  public Mono<Teacher> findbyId(String idTeacher) {
    return repository.findById(idTeacher);
  }

}
