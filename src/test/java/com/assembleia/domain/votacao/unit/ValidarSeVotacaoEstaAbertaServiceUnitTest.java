package com.assembleia.domain.votacao.unit;

import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.domain.votacao.service.ValidarSeVotacaoEstaAbertaService;
import com.assembleia.domain.votacao.service.ValidarSeVotacaoEstaAbertaService.PautaJaFechadaParaVotacaoException;
import com.assembleia.sk.vo.Descricao;
import com.assembleia.sk.vo.Pergunta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class ValidarSeVotacaoEstaAbertaServiceUnitTest {

  @Mock
  private PautaDomainRepository pautaDomainRepository;

  private ValidarSeVotacaoEstaAbertaService validarSeVotacaoEstaAbertaService;
  private Pauta pauta;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    validarSeVotacaoEstaAbertaService = new ValidarSeVotacaoEstaAbertaService(pautaDomainRepository);

    pauta = Pauta.builder()
        .descricao(Descricao.from("Descrição Pauta"))
        .pergunta(Pergunta.from("Pergunta da Pauta"))
        .dataLimite(LocalDateTime.now())
        .build();
    Mockito.when(pautaDomainRepository.get(pauta.getId())).thenReturn(pauta);
  }

  @Test
  public void deveValidarSePautaJaFechouParaVotacaoPeloStatus() {
    Assertions.assertThrows(PautaJaFechadaParaVotacaoException.class, () -> {
      pauta.fecharPauta();
      validarSeVotacaoEstaAbertaService.executar(pauta.getId());
    });
  }

  @Test
  public void deveValidarSePautaJaFechouParaVotacaoPelaDataLimite() {
    Assertions.assertThrows(PautaJaFechadaParaVotacaoException.class, () -> {
      pauta = Pauta.builder()
          .descricao(Descricao.from("Descrição Pauta"))
          .pergunta(Pergunta.from("Pergunta da Pauta"))
          .dataLimite(LocalDateTime.now().minusMinutes(1))
          .build();
      Mockito.when(pautaDomainRepository.get(pauta.getId())).thenReturn(pauta);
      validarSeVotacaoEstaAbertaService.executar(pauta.getId());
    });
  }

  @Test
  public void deveValidarSePautaAindaEstaAbertaParaVotacaoPelaDataLimite() {
    Assertions.assertDoesNotThrow(() -> {
      pauta = Pauta.builder()
          .descricao(Descricao.from("Descrição Pauta"))
          .pergunta(Pergunta.from("Pergunta da Pauta"))
          .dataLimite(LocalDateTime.now().plusMinutes(1))
          .build();
      Mockito.when(pautaDomainRepository.get(pauta.getId())).thenReturn(pauta);
      validarSeVotacaoEstaAbertaService.executar(pauta.getId());
    });
  }

}
