package com.assembleia.domain.votacao.app;

import com.assembleia.domain.associado.model.Associado;
import com.assembleia.domain.associado.repository.AssociadoDomainRepository;
import com.assembleia.domain.pauta.repository.PautaDomainRepository;
import com.assembleia.domain.votacao.model.Votacao;
import com.assembleia.domain.votacao.repository.VotacaoDomainRepository;
import com.assembleia.domain.votacao.service.ValidarSeAssociadoPodeVotarService;
import com.assembleia.domain.votacao.service.ValidarSeVotacaoEstaAbertaService;
import com.assembleia.domain.votacao.usecase.RegistrarVotacaoUseCase;
import com.assembleia.sk.identifiers.VotacaoId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Transactional
public class RegistrarVotacaoAppService implements RegistrarVotacaoUseCase {

    private final VotacaoDomainRepository repository;
    private final PautaDomainRepository pautaDomainRepository;
    private final AssociadoDomainRepository associadoDomainRepository;
    private final ValidarSeAssociadoPodeVotarService validarSeAssociadoPodeVotarService;
    private final ValidarSeVotacaoEstaAbertaService validarSeVotacaoEstaAbertaService;

    @Override
    public VotacaoId handle(RegistrarVotacao command) {

        Associado associado = associadoDomainRepository.get(command.getAssociadoId());

        validarSeAssociadoPodeVotarService.executar(associado.getCpf(), new RestTemplate());
        validarSeVotacaoEstaAbertaService.executar(command.getPautaId());

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
