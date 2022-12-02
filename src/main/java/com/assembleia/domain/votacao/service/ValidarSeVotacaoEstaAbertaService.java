package com.assembleia.domain.votacao.service;

import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.model.StatusPauta;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.sdk.stereotype.BusinessError;
import com.assembleia.sk.identifiers.PautaId;
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

  public static class PautaJaFechadaParaVotacaoException extends BusinessError {
    private PautaJaFechadaParaVotacaoException() {
      super("Pauta já está fechada para votação!");
    }
  }

}
