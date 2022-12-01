package com.assembleia.query.votacao.app;

import com.assembleia.query.votacao.projection.ContagemVotos;
import com.assembleia.query.votacao.projection.ResultadoVotacao;
import com.assembleia.query.votacao.repository.VotacaoQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class VotacaoQueryAppService {

    private final VotacaoQueryRepository votacaoQueryRepository;

    public ContagemVotos recuperarContagemVotosByPauta(UUID pautaId) {
        return votacaoQueryRepository.getCotagemVotosByPautaId(pautaId);
    }

    public ResultadoVotacao recuperarResultadoVotacaoByPauta(UUID pautaId) {
        return votacaoQueryRepository.recuperarResultadoVotacaoByPautaId(pautaId);
    }

}
