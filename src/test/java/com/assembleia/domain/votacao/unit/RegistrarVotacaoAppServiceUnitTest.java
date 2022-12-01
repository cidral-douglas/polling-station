package com.assembleia.domain.votacao.unit;

import com.assembleia.domain.associado.model.Associado;
import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.domain.votacao.app.RegistrarVotacaoAppService;
import com.assembleia.domain.votacao.model.Votacao;
import com.assembleia.domain.votacao.model.Voto;
import com.assembleia.domain.votacao.repository.VotacaoDomainRepository;
import com.assembleia.domain.votacao.service.ValidarSeAssociadoPodeVotarService;
import com.assembleia.domain.votacao.service.ValidarSeVotacaoEstaAbertaService;
import com.assembleia.domain.votacao.usecase.RegistrarVotacaoUseCase.RegistrarVotacao;
import com.assembleia.sk.identifiers.PautaId;
import com.assembleia.sk.identifiers.VotacaoId;
import com.assembleia.sk.vo.CPF;
import com.assembleia.sk.vo.Descricao;
import com.assembleia.sk.vo.Nome;
import com.assembleia.sk.vo.Pergunta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class RegistrarVotacaoAppServiceUnitTest {

  @Mock
  private VotacaoDomainRepository votacaoDomainRepository;

  @Mock
  private PautaDomainRepository pautaDomainRepository;

  @Mock
  private AssociadoDomainRepository associadoDomainRepository;

  @Mock
  private ValidarSeAssociadoPodeVotarService validarSeAssociadoPodeVotarService;

  @Mock
  private ValidarSeVotacaoEstaAbertaService validarSeVotacaoEstaAbertaService;

  private RegistrarVotacaoAppService registrarVotacaoAppService;

  private Associado associado;
  private Pauta pauta;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    registrarVotacaoAppService = new RegistrarVotacaoAppService(
        votacaoDomainRepository,
        pautaDomainRepository,
        associadoDomainRepository,
        validarSeAssociadoPodeVotarService,
        validarSeVotacaoEstaAbertaService
    );

    associado = Associado.builder()
        .nome(Nome.from("Matheus Bernard"))
        .cpf(CPF.from("35008530054"))
        .cpfDuplicatedConstraint(c -> false)
        .build();
    Mockito.when(associadoDomainRepository.get(associado.getId())).thenReturn(associado);

    pauta = Pauta.builder()
        .descricao(Descricao.from("Descrição Pauta"))
        .pergunta(Pergunta.from("Pergunta da Pauta"))
        .dataLimite(LocalDateTime.now())
        .build();
    Mockito.when(pautaDomainRepository.existsById(pauta.getId())).thenReturn(true);
    Mockito.when(associadoDomainRepository.existsById(associado.getId())).thenReturn(true);
  }

  @Test
  public void deveRegistrarVotacao() {
    RegistrarVotacao registrarVotacao = RegistrarVotacao.builder()
        .associadoId(associado.getId())
        .pautaId(pauta.getId())
        .voto(Voto.SIM)
        .build();
    VotacaoId returnedVotacaoId = registrarVotacaoAppService.handle(registrarVotacao);
    Mockito.verify(votacaoDomainRepository).save(Mockito.any(Votacao.class));
    Assertions.assertNotNull(returnedVotacaoId);
  }

}
