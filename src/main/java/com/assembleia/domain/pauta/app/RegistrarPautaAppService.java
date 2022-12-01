package com.assembleia.domain.pauta.app;

import com.assembleia.domain.pauta.model.Pauta;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.domain.pauta.usecase.RegistrarPautaUseCase;
import com.assembleia.sk.identifiers.PautaId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarPautaAppService implements RegistrarPautaUseCase {

    private final PautaDomainRepository repository;

    @Override
    public PautaId handle(@Valid RegistrarPauta command) {

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
