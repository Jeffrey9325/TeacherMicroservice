package com.everis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "teachers")
@JsonPropertyOrder({"id", "fullName", "gender",
    "dateofBirth", "typeDocument", "documentNumber"})
public class Teacher implements Serializable {

	private static final long serialVersionUID = -7582893407136072654L;
  /**
   * id.
   */
  @Id
   private String id;
  /**
   * full name.
   */
  @NotEmpty(message = "should not be empty")
  private String fullName;
  /**
   * gender.
   */
  @NotEmpty(message = "should not be empty")
  private String gender;
  /**
   * date of birth.
   */
  @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
  @NotNull
  private Date dateofBirth;
  /**
   * type of identification document.
   */
  @NotEmpty(message = "should not be empty")
  private String typeDocument;
  /**
   * identification document number.
   */
  @Size(min = 8, max = 8, message = "must contain 8 characters")
  private String documentNumber;
    
}