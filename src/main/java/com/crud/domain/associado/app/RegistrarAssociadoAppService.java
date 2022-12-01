package com.crud.domain.associado.app;

import com.crud.domain.associado.model.Associado;
import com.crud.domain.associado.repository.AssociadoDomainRepository;
import com.crud.domain.associado.usecase.RegistrarAssociadoUseCase;
import com.crud.sk.identifiers.AssociadoId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarAssociadoAppService implements RegistrarAssociadoUseCase {

    private final AssociadoDomainRepository repository;

    @Override
    public AssociadoId handle(RegistrarAssociado command) {

        Associado associado = Associado.builder()
                .nome(command.getNome())
                .cpf(command.getCpf())
                .cpfDuplicatedConstraint(repository::existsByCpf)
                .build();

        repository.save(associado);

        return associado.getId();
    }

    @Override
    @EventListener
    public void on(AssociadoRegistrado event) {}

}
