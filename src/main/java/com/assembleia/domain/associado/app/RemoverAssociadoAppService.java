package com.assembleia.domain.associado.app;

import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.associado.usecase.RemoverAssociadoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RemoverAssociadoAppService implements RemoverAssociadoUseCase {

    private final AssociadoDomainRepository repository;

    @Override
    public void handle(RemoverAssociado comando) {
        repository.findById(comando.getId())
            .ifPresent(associado -> {
                associado.remover();
                repository.save(associado);
            });
    }

    @Override
    @EventListener
    public void on(AssociadoRemovido event) {}
}

