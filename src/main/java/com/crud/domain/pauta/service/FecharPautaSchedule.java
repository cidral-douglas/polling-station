package com.crud.domain.pauta.service;

import com.crud.domain.pauta.model.Pauta;
import com.crud.domain.pauta.model.StatusPauta;
import com.crud.domain.pauta.repository.PautaDomainRepository;
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

  private final long CINCO_MINUTOS = (1000 * 60 * 5);

  @Scheduled(fixedDelay = CINCO_MINUTOS)
  public void executar() {
    List<Pauta> pautasAbertas = repository.findAllByStatus(StatusPauta.ABERTA);
    pautasAbertas.forEach(pauta -> {
      if(LocalDateTime.now().isAfter(pauta.getDataLimite())) {
        pauta.fecharPauta();
        repository.save(pauta);
      }
    });
  }
}
