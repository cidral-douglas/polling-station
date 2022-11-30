package com.crud.domain.votacao.service;

import com.crud.domain.pauta.model.Pauta;
import com.crud.domain.pauta.model.StatusPauta;
import com.crud.domain.pauta.repository.PautaDomainRepository;
import com.crud.sdk.stereotype.BusinessError;
import com.crud.sk.identifiers.PautaId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ValidarSeVotacaoEstaAbertaService {

  private final PautaDomainRepository pautaDomainRepository;

  public void executar(PautaId pautaId) {
    Pauta pauta = pautaDomainRepository.get(pautaId);
    if(StatusPauta.FECHADA.equals(pauta.getStatus()) || LocalDateTime.now().isAfter(pauta.getDataLimite())) {
      throw new PautaJaFechadaParaVotacaoException();
    }
  }

  private static class PautaJaFechadaParaVotacaoException extends BusinessError {
    private static final long serialVersionUID = 991370116545156345L;
  }

}
