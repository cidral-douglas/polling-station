package com.assembleia.domain.votacao.model;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ValidacaoCpfDto {

  @Enumerated(EnumType.STRING)
  private StatusValidacaoCpf status;
}
