package com.assembleia.domain.pauta.unit;

import com.assembleia.domain.pauta.app.RegistrarPautaAppService;
import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.domain.pauta.usecase.RegistrarPautaUseCase.RegistrarPauta;
import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.vo.Descricao;
import com.assembleia.sk.vo.Pergunta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class RegistrarPautaAppServiceUnitTest {

  @Mock
  private PautaDomainRepository pautaDomainRepository;

  private RegistrarPautaAppService registrarPautaAppService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    registrarPautaAppService = new RegistrarPautaAppService(pautaDomainRepository);
  }

  @Test
  public void deveRegistrarPauta() {
    RegistrarPauta registrarPauta = RegistrarPauta.builder()
        .descricao(Descricao.from("Descrição da Pauta"))
        .pergunta(Pergunta.from("Você concorda com o que está em pauta?"))
        .dataLimite(LocalDateTime.now())
        .build();
    PautaId returnedPautaId = registrarPautaAppService.handle(registrarPauta);
    Mockito.verify(pautaDomainRepository).save(Mockito.any(Pauta.class));
    Assertions.assertNotNull(returnedPautaId);
  }

}
