package com.assembleia.sk.vo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class CPFUnitTest {

  private Validator validator;

  @BeforeEach
  public void setup() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void deveValidarSeCpfPossuiApenasDigitos() {
    Assertions.assertThrows(CPF.CPFDeveConterApenasDigitosException.class, () -> {
      CPF.from("5223798503a");
    });
  }

  @Test
  public void deveValidarSeCpfValido() {
    Assertions.assertThrows(CPF.CPFInvalidoException.class, () -> {
      CPF.from("42237485034");
    });

    Assertions.assertThrows(CPF.CPFInvalidoException.class, () -> {
      CPF.from("99999999999");
    });
  }

  @Test
  public void deveValidarSeCpfCom11Digitos() {
    Assertions.assertThrows(CPF.CPFInvalidoException.class, () -> {
      CPF.from("4223748503487646");
    });
  }

  @Test
  public void deveValidarNotBlankESize() {
    CPF cpf = CPF.NAO_INFORMADO;
    Set<ConstraintViolation<CPF>> violations = validator.validate(cpf);
    Assertions.assertEquals(violations.size(), 2);
  }
}
