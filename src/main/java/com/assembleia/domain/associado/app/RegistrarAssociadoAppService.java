package com.assembleia.domain.associado.app;

import com.assembleia.domain.associado.model.Associado;
import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.associado.usecase.RegistrarAssociadoUseCase;
import com.assembleia.sk.identifiers.AssociadoId;
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
