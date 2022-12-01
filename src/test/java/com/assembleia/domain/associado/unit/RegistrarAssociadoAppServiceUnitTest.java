package com.assembleia.domain.associado.unit;

import com.assembleia.domain.associado.app.RegistrarAssociadoAppService;
import com.assembleia.domain.associado.model.Associado;
import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.associado.usecase.RegistrarAssociadoUseCase.RegistrarAssociado;
import com.assembleia.sk.identifiers.AssociadoId;
import com.assembleia.sk.vo.CPF;
import com.assembleia.sk.vo.Nome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RegistrarAssociadoAppServiceUnitTest {

  @Mock
  private AssociadoDomainRepository associadoDomainRepository;

  private RegistrarAssociadoAppService registrarAssociadoAppService;

  private Nome nome = Nome.from("José Cidral");
  private CPF cpf = CPF.from("52237985030");

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    registrarAssociadoAppService = new RegistrarAssociadoAppService(associadoDomainRepository);
  }

  @Test
  public void deveRegistrarAssociado() {
    RegistrarAssociado registrarAssociado = RegistrarAssociado.builder()
        .nome(nome)
        .cpf(cpf)
        .build();
    AssociadoId returnedAssociadoId = registrarAssociadoAppService.handle(registrarAssociado);
    Mockito.verify(associadoDomainRepository).save(Mockito.any(Associado.class));
    Assertions.assertNotNull(returnedAssociadoId);
  }

  @Test
  public void deveValidarAssociadoComCpfDuplicado() {
    RegistrarAssociado registrarAssociado = RegistrarAssociado.builder()
        .nome(nome)
        .cpf(cpf)
        .build();
    Assertions.assertThrows(CPF.CPFDuplicadoException.class, () -> {
      registrarAssociadoAppService.handle(registrarAssociado);
      Mockito.when(associadoDomainRepository.existsByCpf(cpf)).thenReturn(true);
      registrarAssociadoAppService.handle(registrarAssociado);
    });
  }

}
