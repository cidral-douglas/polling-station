package com.assembleia.sk.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class PerguntaUnitTest {

  private Validator validator;

  @BeforeEach
  public void setup() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void deveValidarNotBlank() {
    Pergunta pergunta = Pergunta.NAO_INFORMADA;
    Set<ConstraintViolation<Pergunta>> violations = validator.validate(pergunta);
    Assertions.assertEquals(violations.size(), 1);
  }

  @Test
  public void deveValidarSize() {
    Pergunta pergunta = Pergunta.from("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");
    Set<ConstraintViolation<Pergunta>> violations = validator.validate(pergunta);
    Assertions.assertTrue(pergunta.getValue().length() > 255);
    Assertions.assertEquals(violations.size(), 1);
  }
}
