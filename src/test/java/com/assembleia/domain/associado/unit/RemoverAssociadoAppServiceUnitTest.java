package com.assembleia.domain.associado.unit;

import com.assembleia.domain.associado.app.RemoverAssociadoAppService;
import com.assembleia.domain.associado.model.Associado;
import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.associado.usecase.RemoverAssociadoUseCase.RemoverAssociado;
import com.assembleia.sk.vo.CPF;
import com.assembleia.sk.vo.Nome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class RemoverAssociadoAppServiceUnitTest {

  @Mock
  private AssociadoDomainRepository associadoDomainRepository;

  private RemoverAssociadoAppService removerAssociadoAppService;

  private Associado associado;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    removerAssociadoAppService = new RemoverAssociadoAppService(associadoDomainRepository);
    associado = Associado.builder()
        .nome(Nome.from("José Cidral"))
        .cpf(CPF.from("52237985030"))
        .cpfDuplicatedConstraint(c -> false)
        .build();
  }

  @Test
  public void deveRemoverAssociado() {
    RemoverAssociado removerAssociado = RemoverAssociado.builder()
        .id(associado.getId())
        .build();
    Mockito.when(associadoDomainRepository.findById(associado.getId())).thenReturn(Optional.of(associado));
    removerAssociadoAppService.handle(removerAssociado);
    Mockito.verify(associadoDomainRepository).save(Mockito.any(Associado.class));
  }

}
