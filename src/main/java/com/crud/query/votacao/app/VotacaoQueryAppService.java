package com.crud.query.votacao.app;

import com.crud.query.votacao.projection.ContagemVotos;
import com.crud.query.votacao.repository.VotacaoQueryRepository;
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

}
