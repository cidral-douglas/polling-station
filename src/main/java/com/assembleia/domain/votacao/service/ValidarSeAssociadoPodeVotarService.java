package com.assembleia.domain.votacao.service;

import com.assembleia.domain.votacao.model.StatusValidacaoCpf;
import com.assembleia.domain.votacao.model.ValidacaoCpfDto;
import com.assembleia.sdk.stereotype.BusinessError;
import com.assembleia.sk.vo.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidarSeAssociadoPodeVotarService {

  public void executar(CPF cpf, RestTemplate restTemplate) {
    ValidacaoCpfDto validacaoCpfDto = getValidacaoCpfFromExternalService(cpf, restTemplate);
    if (StatusValidacaoCpf.UNABLE_TO_VOTE.equals(validacaoCpfDto.getStatus())) {
      throw new AssociadoNaoPodeVotarException();
    }
  }

  private ValidacaoCpfDto getValidacaoCpfFromExternalService(CPF cpf, RestTemplate restTemplate) {
    try {
      String uri = "https://user-info.herokuapp.com/users/{cpf}";
      return restTemplate.getForObject(uri.replace("{cpf}", cpf.getValue()), ValidacaoCpfDto.class);
    } catch (RuntimeException e){
      // Devido ao serviço de validação de CPF estar fora do ar, não foi realizado nenhuma tratativa o erro.
      e.printStackTrace();
      return null;
    }
  }

  public static class AssociadoNaoPodeVotarException extends BusinessError {
    private AssociadoNaoPodeVotarException() {
      super("Associado não pode votar!");
    }
  }
}
