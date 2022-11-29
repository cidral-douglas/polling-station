package com.crud.domain.pauta.app;

import com.crud.domain.pauta.model.Pauta;
import com.crud.domain.pauta.repository.PautaDomainRepository;
import com.crud.domain.pauta.usecase.RegistrarPautaUseCase;
import com.crud.sk.identifiers.PautaId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarPautaAppService implements RegistrarPautaUseCase {

    private final PautaDomainRepository repository;

    @Override
    public PautaId handle(RegistrarPauta command) {

        Pauta pauta = Pauta.builder()
                .descricao(command.getDescricao())
                .pergunta(command.getPergunta())
                .dataLimite(command.getDataLimite())
                .build();

        repository.save(pauta);

        return pauta.getId();
    }

    @Override
    @EventListener
    public void on(PautaRegistrada event) {}

}
