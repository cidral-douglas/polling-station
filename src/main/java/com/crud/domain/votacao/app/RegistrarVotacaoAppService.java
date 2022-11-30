package com.crud.domain.votacao.app;

import com.crud.domain.associado.model.Associado;
import com.crud.domain.associado.repository.AssociadoDomainRepository;
import com.crud.domain.pauta.repository.PautaDomainRepository;
import com.crud.domain.votacao.model.ValidacaoCpfDto;
import com.crud.domain.votacao.model.Votacao;
import com.crud.domain.votacao.repository.VotacaoDomainRepository;
import com.crud.domain.votacao.service.ValidarSeAssociadoPodeVotarService;
import com.crud.domain.votacao.usecase.RegistrarVotacaoUseCase;
import com.crud.sk.identifiers.VotacaoId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarVotacaoAppService implements RegistrarVotacaoUseCase {

    private final VotacaoDomainRepository repository;
    private final PautaDomainRepository pautaDomainRepository;
    private final AssociadoDomainRepository associadoDomainRepository;
    private final ValidarSeAssociadoPodeVotarService validarSeAssociadoPodeVotarService;

    @Override
    public VotacaoId handle(RegistrarVotacao command) {

        Associado associado = associadoDomainRepository.get(command.getAssociadoId());

        validarSeAssociadoPodeVotarService.executar(associado.getCpf());

        Votacao votacao = Votacao.builder()
                .pautaId(command.getPautaId())
                .pautaIdExistsConstraint(pautaDomainRepository::existsById)
                .associadoId(command.getAssociadoId())
                .associadoIdExistsConstraint(associadoDomainRepository::existsById)
                .voto(command.getVoto())
                .pautaIdAndAssociadoIdExistsConstraint(repository::existsByPautaIdAndAssociadoId)
                .build();

        repository.save(votacao);

        return votacao.getId();
    }

    @Override
    @EventListener
    public void on(VotacaoRegistrada event) {}

}
