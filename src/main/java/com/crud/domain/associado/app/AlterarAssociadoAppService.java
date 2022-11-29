package com.crud.domain.associado.app;

import com.crud.domain.associado.model.Associado;
import com.crud.domain.associado.repository.AssociadoDomainRepository;
import com.crud.domain.associado.usecase.AlterarAssociadoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor

@Service
@Transactional
public class AlterarAssociadoAppService implements AlterarAssociadoUseCase {

    private final AssociadoDomainRepository repository;

    @Override
    public void handle(AlterarAssociado command) {

        Associado associado = repository.get(command.getId());

        associado.alterar()
            .nome(command.getNome())
            .apply();

        repository.save(associado);
    }

    @Override
    @EventListener
    public void on(AssociadoAlterado event) {}

}