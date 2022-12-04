package com.assembleia.adapter.in.schedule;

import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.model.StatusPauta;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.adapter.in.publisher.FecharPautaPublisher;
import com.assembleia.query.votacao.projection.ResultadoVotacao;
import com.assembleia.query.votacao.repository.VotacaoQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class FecharPautaSchedule {

  private final PautaDomainRepository repository;
  private final VotacaoQueryRepository votacaoQueryRepository;
  private final FecharPautaPublisher fecharPautaPublisher;

  private final long CINCO_MINUTOS = (1000 * 60 * 5);

  @Scheduled(fixedDelay = CINCO_MINUTOS)
  public void executar() {
    List<Pauta> pautasAbertas = repository.findAllByStatus(StatusPauta.ABERTA);
    pautasAbertas.forEach(pauta -> {
      if(LocalDateTime.now().isAfter(pauta.getDataLimite())) {
        pauta.fecharPauta();
        repository.save(pauta);
        ResultadoVotacao resultadoVotacao = votacaoQueryRepository.recuperarResultadoVotacaoByPautaId(pauta.getId().getValue());
        fecharPautaPublisher.enviarMensagem("Pauta " + pauta.getId().getValue() + " foi fechada! O Resultado foi: " + resultadoVotacao.getResultado());
      }
    });
  }
}
