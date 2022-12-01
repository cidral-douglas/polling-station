package com.assembleia.domain.associado.unit;

import com.assembleia.domain.associado.app.AlterarAssociadoAppService;
import com.assembleia.domain.associado.model.Associado;
import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.associado.usecase.AlterarAssociadoUseCase.AlterarAssociado;
import com.assembleia.sk.vo.CPF;
import com.assembleia.sk.vo.Nome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AlterarAssociadoAppServiceUnitTest {

  @Mock
  private AssociadoDomainRepository associadoDomainRepository;

  private AlterarAssociadoAppService alterarAssociadoAppService;

  private Associado associado;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    alterarAssociadoAppService = new AlterarAssociadoAppService(associadoDomainRepository);
    associado = Associado.builder()
        .nome(Nome.from("José Cidral"))
        .cpf(CPF.from("52237985030"))
        .cpfDuplicatedConstraint(c -> false)
        .build();
  }

  @Test
  public void deveAlterarAssociado() {
    AlterarAssociado alterarAssociado = AlterarAssociado.builder()
        .id(associado.getId())
        .nome(Nome.from("Matheus Cidral"))
        .build();
    Mockito.when(associadoDomainRepository.get(associado.getId())).thenReturn(associado);
    alterarAssociadoAppService.handle(alterarAssociado);
    Mockito.verify(associadoDomainRepository).save(Mockito.any(Associado.class));
  }

}
