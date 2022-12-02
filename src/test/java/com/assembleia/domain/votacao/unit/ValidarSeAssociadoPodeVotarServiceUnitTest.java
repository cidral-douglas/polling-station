package com.assembleia.domain.votacao.unit;

import com.assembleia.domain.votacao.model.StatusValidacaoCpf;
import com.assembleia.domain.votacao.model.ValidacaoCpfDto;
import com.assembleia.domain.votacao.service.ValidarSeAssociadoPodeVotarService;
import com.assembleia.domain.votacao.service.ValidarSeAssociadoPodeVotarService.AssociadoNaoPodeVotarException;
import com.assembleia.sk.vo.CPF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class ValidarSeAssociadoPodeVotarServiceUnitTest {

  @Mock
  private RestTemplate restTemplate;

  private ValidarSeAssociadoPodeVotarService validarSeAssociadoPodeVotarService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    validarSeAssociadoPodeVotarService = new ValidarSeAssociadoPodeVotarService();
  }

  @Test
  public void deveValidarAssociadoNaoPodeVotarPeloCpf() {
    Assertions.assertThrows(AssociadoNaoPodeVotarException.class, () -> {
      CPF cpf = CPF.from("35008530054");

      ValidacaoCpfDto validacaoCpfDto = new ValidacaoCpfDto();
      validacaoCpfDto.setStatus(StatusValidacaoCpf.UNABLE_TO_VOTE);

      String uri = "https://user-info.herokuapp.com/users/" + cpf.getValue();
      Mockito.when(restTemplate.getForObject(uri, ValidacaoCpfDto.class)).thenReturn(validacaoCpfDto);
      validarSeAssociadoPodeVotarService.executar(cpf, restTemplate);
    });
  }

  @Test
  public void deveValidarAssociadoPodeVotarPeloCpf() {
    Assertions.assertDoesNotThrow(() -> {
      CPF cpf = CPF.from("35008530054");

      ValidacaoCpfDto validacaoCpfDto = new ValidacaoCpfDto();
      validacaoCpfDto.setStatus(StatusValidacaoCpf.ABLE_TO_VOTE);

      String uri = "https://user-info.herokuapp.com/users/" + cpf.getValue();
      Mockito.when(restTemplate.getForObject(uri, ValidacaoCpfDto.class)).thenReturn(validacaoCpfDto);
      validarSeAssociadoPodeVotarService.executar(cpf, restTemplate);
    });
  }

}
