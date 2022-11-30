package com.crud.domain.votacao.service;

import com.crud.domain.votacao.model.StatusValidacaoCpf;
import com.crud.domain.votacao.model.ValidacaoCpfDto;
import com.crud.sdk.stereotype.BusinessError;
import com.crud.sk.vo.CPF;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidarSeAssociadoPodeVotarService {

  public void executar(CPF cpf) {
    try {
      String uri = "https://user-info.herokuapp.com/users/{cpf}";
      RestTemplate restTemplate = new RestTemplate();
      ValidacaoCpfDto validacaoCpfDto = restTemplate.getForObject(uri.replace("{cpf}", cpf.getValue()), ValidacaoCpfDto.class);
      if (StatusValidacaoCpf.UNABLE_TO_VOTE.equals(validacaoCpfDto.getStatus())) {
        throw new AssociadoNaoPodeVotarException();
      }

    } catch (RuntimeException e){
      // Devido ao serviço de validação de CPF estar fora do ar, não foi realizado nenhuma tratativa o erro.
      e.printStackTrace();
    }
  }

  private static class AssociadoNaoPodeVotarException extends BusinessError {
    private AssociadoNaoPodeVotarException() {
      super("Associado não pode votar!");
    }
  }
}
